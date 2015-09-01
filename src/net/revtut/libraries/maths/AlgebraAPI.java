package net.revtut.libraries.maths;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * Algebra Library.
 *
 * <P>A library with methods which envolve to maths and mathematics.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public final class AlgebraAPI {

    /**
     * Constructor of AlgebraAPI
     */
    private AlgebraAPI() {}

    /**
     * Make a location look at another location, in other words, change the pitch/yaw in such manner that,
     * if applied to a player or entity, it will look at the specified location
     *
     * @param location location to set pitch and yaw
     * @param lookAt location to look at
     * @return location that will look lookAt
     */
    public static Location locationLookAt(Location location, Location lookAt) {
        // Values of change in distance (make it relative)
        double deltaX = lookAt.getX() - location.getX();
        double deltaY = lookAt.getY() - location.getY();
        double deltaZ = lookAt.getZ() - location.getZ();

        // Values of the new location
        double x = location.getX(), y = location.getY(), z = location.getZ();
        float yaw = location.getYaw(), pitch;

        // Set yaw
        if (deltaX != 0) {
            // Set yaw start value based on deltaX
            if (deltaX < 0)
                yaw = (float) (1.5 * Math.PI);
            else
                yaw = (float) (0.5 * Math.PI);
            yaw -= Math.atan(deltaZ / deltaX);
        } else if (deltaZ < 0)
            yaw = (float) Math.PI;

        // Get the distance from deltaX/deltaZ
        double deltaXZ = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaZ, 2));

        // Set pitch
        pitch = (float) -Math.atan(deltaY / deltaXZ);

        // Set values, convert to degrees (invert the yaw since Bukkit uses a different yaw dimension format)
        yaw *= -1 * 180f / (float) Math.PI;
        pitch *= 180f / (float) Math.PI;

        return new Location(location.getWorld(), x, y, z, yaw, pitch);
    }

    /**
     * Get the closest player to a given player
     *
     * @param players list with players to get the closest
     * @param player player to get the closest player
     * @return closest player
     */
    public static Player closestPlayer(List<Player> players, Player player) {
        Player closest = null;
        double minDistance = Integer.MAX_VALUE;
        for(Player target : players) {
            if(target.getUniqueId().equals(player.getUniqueId()))
                continue;

            if(target.getWorld() != player.getWorld())
                continue;

            double distance = distanceBetween(player.getLocation(), target.getLocation());

            if(distance < minDistance){
                minDistance = distance;
                closest = target;
            }
        }
        return closest;
    }

    /**
     * Distance between two locations in meters
     *
     * @param initial initial location
     * @param target target location
     * @return distance in meters
     */
    public static double distanceBetween(Location initial, Location target) {
        int xI = initial.getBlockX(), xF = target.getBlockX();
        int yI = initial.getBlockY(), yF = target.getBlockY();
        int zI = initial.getBlockZ(), zF = target.getBlockZ();

        int deltaX = xF - xI, deltaY = yF - yI, deltaZ = zF - zI;

        return Math.sqrt((deltaX * deltaX) + (deltaY * deltaY) + (deltaZ * deltaZ));
    }

    /**
     * Generates a list with unique random integers
     *
     * @param minInteger minimum integer that may be generated
     * @param maxInteger maximum integer that may be generated
     * @param numberIntegers number of unique integeres to be generated
     * @return list with random integers
     */
    public static List<Integer> getUniqueRandomIntegers(int minInteger, int maxInteger, int numberIntegers) {
        if(maxInteger < numberIntegers)
            return null;
        List<Integer> list = new ArrayList<>();
        int number;
        for(int i = 0; i < numberIntegers; i++) {
            do {
                number = (int) (Math.random() * (maxInteger - minInteger)) + minInteger;
            } while(list.contains(number));
            list.add(number);
        }
        return list;
    }

    /**
     * Get a random vector
     * @return random vector
     */
    public static Vector getRandomVector() {
        double x, y, z;
        x = Math.random() * 2 - 1;
        y = Math.random() * 2 - 1;
        z = Math.random() * 2 - 1;

        return new Vector(x, y, z).normalize();
    }

    /**
     * Get the player that was hit by another one
     *
     * @param player player to get hit target
     * @return player that was hit
     */
    public static Player getHitPlayer(Player player) {
        final int ATTACK_REACH = 4;

        Location playerPos = player.getEyeLocation();
        Vector playerDir = playerPos.getDirection();

        Vector playerStart = playerPos.toVector();
        Vector playerEnd = playerStart.add(playerDir.multiply(ATTACK_REACH));

        Player hit = null;

        // Get nearby entities
        for (Entity entity : player.getNearbyEntities(5, 5, 5)) {
            if(!(entity instanceof Player))
                continue;

            Player target = (Player) entity;

            // If player is visible no need to check
            if (player.canSee(target))
                continue;

            // Bounding box of the given player
            Vector targetPos = target.getLocation().toVector();
            Vector minimum = targetPos.add(new Vector(-0.5, 0, 0.5));
            Vector maximum = targetPos.add(new Vector(0.5, 1.67, 0.5));

            if (hasIntersection(playerStart, playerEnd, minimum, maximum))
                if (hit == null || AlgebraAPI.distanceBetween(hit.getLocation(), playerPos) > AlgebraAPI.distanceBetween(target.getLocation(), playerPos))
                    hit = target;
        }

        return hit;
    }

    /**
     * Check if a box is inside intersected between the vector one and vector two
     *
     * @param p1 first vector
     * @param p2 second vector
     * @param min minimum corner
     * @param max maximum corner
     * @return true if intersects
     */
    public static boolean hasIntersection(Vector p1, Vector p2, Vector min, Vector max) {
        double EPSILON = 1e-9f;
        Vector d = p2.subtract(p1).multiply(0.5f);
        Vector e = max.subtract(min).multiply(0.5f);
        Vector c = p1.add(d).subtract(min.add(max)).multiply(0.5f);
        Vector ad = d.setX(Math.abs(d.getX())).setY(Math.abs(d.getY())).setZ(Math.abs(d.getX())); // Returns same vector with all components positive

        if (Math.abs(c.getX()) > e.getX() + ad.getX())
            return false;
        if (Math.abs(c.getY()) > e.getY() + ad.getY())
            return false;
        if (Math.abs(c.getZ()) > e.getZ() + ad.getZ())
            return false;

        if (Math.abs(d.getY() * c.getZ() - d.getZ() * c.getY()) > e.getY() * ad.getZ() + e.getZ() * ad.getY() + EPSILON)
            return false;
        if (Math.abs(d.getZ() * c.getX() - d.getX() * c.getZ()) > e.getZ() * ad.getX() + e.getX() * ad.getZ() + EPSILON)
            return false;
        if (Math.abs(d.getX() * c.getY() - d.getY() * c.getX()) > e.getX() * ad.getY() + e.getY() * ad.getX() + EPSILON)
            return false;

        return true;
    }

    /**
     * Check if a location is inside AABB
     * @param location location to be checked
     * @param minimum minimum location
     * @param maximum maximum location
     * @return true if is inside AABB, false otherwise
     */
    public static boolean isInAABB(Location location, Location minimum, Location maximum){
        return location.getX() >= minimum.getX() && location.getX() <= maximum.getX() && location.getY() >= minimum.getY() && location.getY() <= maximum.getY() && location.getZ() >= minimum.getZ() && location.getZ() <= maximum.getZ();
    }

    /**
     * Rotate a vector around the X axis
     * @param vector vector to be rotated
     * @param angle angle to rotate
     * @return rotated vector
     */
    public static Vector rotateAroundAxisX(Vector vector, double angle) {
        double y, z, cos, sin;
        cos = Math.cos(angle);
        sin = Math.sin(angle);
        y = vector.getY() * cos - vector.getZ() * sin;
        z = vector.getY() * sin + vector.getZ() * cos;
        return vector.setY(y).setZ(z);
    }

    /**
     * Rotate a vector around the Y axis
     * @param vector vector to be rotated
     * @param angle angle to rotate
     * @return rotated vector
     */
    public static Vector rotateAroundAxisY(Vector vector, double angle) {
        double x, z, cos, sin;
        cos = Math.cos(angle);
        sin = Math.sin(angle);
        x = vector.getX() * cos + vector.getZ() * sin;
        z = vector.getX() * -sin + vector.getZ() * cos;
        return vector.setX(x).setZ(z);
    }

    /**
     * Rotate a vector around the Z axis
     * @param vector vector to be rotated
     * @param angle angle to rotate
     * @return rotated vector
     */
    public static Vector rotateAroundAxisZ(Vector vector, double angle) {
        double x, y, cos, sin;
        cos = Math.cos(angle);
        sin = Math.sin(angle);
        x = vector.getX() * cos - vector.getY() * sin;
        y = vector.getX() * sin + vector.getY() * cos;
        return vector.setX(x).setY(y);
    }

    /**
     * Get a list of locations that create a circle around a position counter clockwise
     * @param center center of the circle
     * @param startAngle start angle of the circle
     * @param slope slope of the circle
     * @param radius radius of the circle
     * @param numberPoints number of points of the circle
     * @param rotation rotation of the circle
     * @return list with circle points
     */
    public static List<Location> getCircle(Location center, double startAngle, double slope, double radius, int numberPoints, Rotation rotation) {
        World world = center.getWorld();

        double incrementAngle = (2 * Math.PI) / numberPoints;
        double incrementY = Math.tan(slope) * radius * 2 / numberPoints;
        double y = center.getY() - incrementY * numberPoints / 4;

        List<Location> locations = new ArrayList<>();
        for(int i = 0; i < numberPoints; i++) {
            double angle;
            if(rotation == Rotation.COUNTER_CLOCKWISE)
                angle = startAngle + i * incrementAngle;
            else
                angle = startAngle - i * incrementAngle;
            double x = center.getX() + (radius * Math.cos(angle));
            double z = center.getZ() + (radius * Math.sin(angle));
            locations.add(new Location(world, x, y, z));

            if(i < numberPoints / 2)
                y += incrementY;
            else
                y -= incrementY;
        }

        return locations;
    }

    /**
     * Get a list of locations that create a helix around a position counter clockwise
     * @param center center of the helix
     * @param height height of the helix
     * @param startAngle start angle of the helix
     * @param radius radius of the circle
     * @param numberPoints number of points per circle
     * @param numberCircles number of circles of the helix
     * @param rotation rotation of the helix
     * @return list with helix points
     */
    public static List<Location> getHelix(Location center, double height, double startAngle, double radius, int numberPoints, int numberCircles, Rotation rotation) {
        List<Location> locations = new ArrayList<>();
        for(int i = 0; i < numberCircles; i++) {
            locations.addAll(getCircle(center, startAngle, 0, radius, numberPoints, rotation));
        }

        numberPoints *= numberCircles;
        double incrementY = height / numberPoints;

        for(int i = 0; i < numberPoints; i++) {
            double y = center.getY() + incrementY * i;
            locations.get(i).setY(y);
        }

        return locations;
    }

    /**
     * Get a list of locations that create a sphere
     * @param center center of the sphere
     * @param radius radius of the circle
     * @param numberPoints number of points of the sphere
     * @return list with sphere points
     */
    public static List<Location> getSphere(Location center, double radius, int numberPoints) {
        List<Location> locations = new ArrayList<>();
        for(int i = 0; i < numberPoints; i++)
            locations.add(center.clone().add(getRandomVector().multiply(radius)));

        return locations;
    }

    /**
     * Get a list of locations that create a tornado around a position counter clockwise
     * @param bottom bottom location of the tornado
     * @param height height of the tornado
     * @param maxRadius maximum radius of the tornado
     * @param numberPoints number of points of the tornado
     * @param numberCircles number of circles of the tornado
     * @param rotation rotation of the helix
     * @return list with sphere points
     */
    public static List<Location> getTornado(Location bottom, double height, double maxRadius, int numberPoints, int numberCircles, Rotation rotation) {
        int index = 0;
        int multiplier = 0;

        int totalPoints = numberPoints * numberCircles;
        double incrementRadius = maxRadius / totalPoints;
        double incrementY = height / totalPoints;

        List<Location> locations = new ArrayList<>();
        for(int i = 0; i < numberCircles; i++) {
            for(int j = 0; j < numberPoints; j++) {
                double y = bottom.getY() + incrementY * multiplier;
                double radius = incrementRadius * multiplier;
                Location location = getCircle(bottom, 0, 0, radius, numberPoints, rotation).get(index);
                location.setY(y);
                locations.add(location);
                index++;
                multiplier++;
            }
            index = 0;
        }

        return locations;
    }
}