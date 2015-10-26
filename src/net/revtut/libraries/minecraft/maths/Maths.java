package net.revtut.libraries.minecraft.maths;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * Maths Library.
 *
 * <P>A library with methods which envolve mathematics.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public final class Maths {

    /**
     * Constructor of Maths
     */
    private Maths() {}

    /**
     * Make a location look at another location, in other words, change the pitch/yaw in such manner that,
     * if applied to a player or entity, it will look at the specified location
     * @param location location to set pitch and yaw
     * @param lookAt location to look at
     * @return location that will look lookAt
     */
    public static void setLocationLookAt(final Location location, final Location lookAt) {
        // Values of change in distance (make it relative)
        final double deltaX = lookAt.getX() - location.getX();
        final double deltaY = lookAt.getY() - location.getY();
        final double deltaZ = lookAt.getZ() - location.getZ();

        // Values of the new location
        final double x = location.getX();
        final double y = location.getY();
        final double z = location.getZ();
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
        final double deltaXZ = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaZ, 2));

        // Set pitch
        pitch = (float) -Math.atan(deltaY / deltaXZ);

        // Set values, convert to degrees (invert the yaw since Bukkit uses a different yaw dimension format)
        yaw *= -1 * 180f / (float) Math.PI;
        pitch *= 180f / (float) Math.PI;

        // Update location
        location.setX(x);
        location.setY(y);
        location.setZ(z);
        location.setYaw(yaw);
        location.setPitch(pitch);
    }

    /**
     * Get the closest player to a given player
     *
     * @param players list with players to get the closest
     * @param player player to get the closest player
     * @return closest player
     */
    public static Player getClosestPlayer(final List<Player> players, final Player player) {
        Player closest = null;
        double minDistance = Integer.MAX_VALUE;
        for(final Player target : players) {
            if(target.getUniqueId().equals(player.getUniqueId()))
                continue;

            if(target.getWorld() != player.getWorld())
                continue;

            final double distance = distanceBetween(player.getLocation(), target.getLocation());

            if(distance < minDistance){
                minDistance = distance;
                closest = target;
            }
        }
        return closest;
    }

    /**
     * Get a random vector
     * @return random vector
     */
    public static Vector getRandomVector() {
        final double x;
        final double y;
        final double z;
        x = Math.random() * 2 - 1;
        y = Math.random() * 2 - 1;
        z = Math.random() * 2 - 1;

        return new Vector(x, y, z).normalize();
    }

    /**
     * Get the player that was hit by another one
     * @param player player to get hit target
     * @return player that was hit
     */
    public static Player getHitPlayer(final Player player) {
        final int ATTACK_REACH = 4;

        final Location playerPos = player.getEyeLocation();
        final Vector playerDir = playerPos.getDirection();

        final Vector playerStart = playerPos.toVector();
        final Vector playerEnd = playerStart.add(playerDir.multiply(ATTACK_REACH));

        Player hit = null;

        // Get nearby entities
        for (final Entity entity : player.getNearbyEntities(5, 5, 5)) {
            if(!(entity instanceof Player))
                continue;

            final Player target = (Player) entity;

            // If player is visible no need to check
            if (player.canSee(target))
                continue;

            // Bounding box of the given player
            final Vector targetPos = target.getLocation().toVector();
            final Vector minimum = targetPos.add(new Vector(-0.5, 0, 0.5));
            final Vector maximum = targetPos.add(new Vector(0.5, 1.67, 0.5));

            if (hasIntersection(playerStart, playerEnd, minimum, maximum))
                if (hit == null || Maths.distanceBetween(hit.getLocation(), playerPos) > Maths.distanceBetween(target.getLocation(), playerPos))
                    hit = target;
        }

        return hit;
    }

    /**
     * Generates a list with unique random integers
     *
     * @param minInteger minimum integer that may be generated
     * @param maxInteger maximum integer that may be generated
     * @param numberIntegers number of unique integeres to be generated
     * @return list with random integers
     */
    public static List<Integer> getUniqueRandomIntegers(final int minInteger, final int maxInteger, final int numberIntegers) {
        if(maxInteger < numberIntegers)
            return null;
        final List<Integer> list = new ArrayList<>();
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
     * Distance between two locations in meters
     *
     * @param initial initial location
     * @param target target location
     * @return distance in meters
     */
    public static double distanceBetween(final Location initial, final Location target) {
        final int xI = initial.getBlockX();
        final int xF = target.getBlockX();
        final int yI = initial.getBlockY();
        final int yF = target.getBlockY();
        final int zI = initial.getBlockZ();
        final int zF = target.getBlockZ();

        final int deltaX = xF - xI;
        final int deltaY = yF - yI;
        final int deltaZ = zF - zI;

        return Math.sqrt((deltaX * deltaX) + (deltaY * deltaY) + (deltaZ * deltaZ));
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
    public static boolean hasIntersection(final Vector p1, final Vector p2, final Vector min, final Vector max) {
        final double EPSILON = 1e-9f;
        final Vector d = p2.subtract(p1).multiply(0.5f);
        final Vector e = max.subtract(min).multiply(0.5f);
        final Vector c = p1.add(d).subtract(min.add(max)).multiply(0.5f);
        final Vector ad = d.setX(Math.abs(d.getX())).setY(Math.abs(d.getY())).setZ(Math.abs(d.getX())); // Returns same vector with all components positive

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
    public static boolean isInAABB(final Location location, final Location minimum, final Location maximum){
        return location.getX() >= minimum.getX() && location.getX() <= maximum.getX() && location.getY() >= minimum.getY() && location.getY() <= maximum.getY() && location.getZ() >= minimum.getZ() && location.getZ() <= maximum.getZ();
    }

    /**
     * Rotate a vector around the X axis
     * @param vector vector to be rotated
     * @param angle angle to rotate
     * @return rotated vector
     */
    public static Vector rotateAroundAxisX(final Vector vector, final double angle) {
        final double cos = Math.cos(angle);
        final double sin = Math.sin(angle);
        final double y =  vector.getY() * cos - vector.getZ() * sin;
        final double z = vector.getY() * sin + vector.getZ() * cos;
        return vector.setY(y).setZ(z);
    }

    /**
     * Rotate a vector around the Y axis
     * @param vector vector to be rotated
     * @param angle angle to rotate
     * @return rotated vector
     */
    public static Vector rotateAroundAxisY(final Vector vector, final double angle) {
        final double cos = Math.cos(angle);
        final double sin = Math.sin(angle);
        final double x =  vector.getX() * cos + vector.getZ() * sin;
        final double z = vector.getX() * -sin + vector.getZ() * cos;
        return vector.setX(x).setZ(z);
    }

    /**
     * Rotate a vector around the Z axis
     * @param vector vector to be rotated
     * @param angle angle to rotate
     * @return rotated vector
     */
    public static Vector rotateAroundAxisZ(final Vector vector, final double angle) {
        final double cos = Math.cos(angle);
        final double sin = Math.sin(angle);
        final double x =  vector.getX() * cos - vector.getY() * sin;
        final double y = vector.getX() * sin + vector.getY() * cos;
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
    public static List<Location> getCircle(final Location center, final double startAngle, final double slope, final double radius, final int numberPoints, final Rotation rotation) {
        final World world = center.getWorld();

        final double incrementAngle = (2 * Math.PI) / numberPoints;
        final double incrementY = Math.tan(slope) * radius * 2 / numberPoints;
        double y = center.getY() - incrementY * numberPoints / 4;

        final List<Location> locations = new ArrayList<>();
        for(int i = 0; i < numberPoints; i++) {
            final double angle;
            if(rotation == Rotation.COUNTER_CLOCKWISE)
                angle = startAngle + i * incrementAngle;
            else
                angle = startAngle - i * incrementAngle;
            final double x = center.getX() + (radius * Math.cos(angle));
            final double z = center.getZ() + (radius * Math.sin(angle));
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
    public static List<Location> getHelix(final Location center, final double height, final double startAngle, final double radius, int numberPoints, final int numberCircles, final Rotation rotation) {
        final List<Location> locations = new ArrayList<>();
        for(int i = 0; i < numberCircles; i++) {
            locations.addAll(getCircle(center, startAngle, 0, radius, numberPoints, rotation));
        }

        numberPoints *= numberCircles;
        final double incrementY = height / numberPoints;

        for(int i = 0; i < numberPoints; i++) {
            final double y = center.getY() + incrementY * i;
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
    public static List<Location> getSphere(final Location center, final double radius, final int numberPoints) {
        final List<Location> locations = new ArrayList<>();
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
    public static List<Location> getTornado(final Location bottom, final double height, final double maxRadius, final int numberPoints, final int numberCircles, final Rotation rotation) {
        int index = 0;
        int multiplier = 0;

        final int totalPoints = numberPoints * numberCircles;
        final double incrementRadius = maxRadius / totalPoints;
        final double incrementY = height / totalPoints;

        final List<Location> locations = new ArrayList<>();
        for(int i = 0; i < numberCircles; i++) {
            for(int j = 0; j < numberPoints; j++) {
                final double y = bottom.getY() + incrementY * multiplier;
                final double radius = incrementRadius * multiplier;
                final Location location = getCircle(bottom, 0, 0, radius, numberPoints, rotation).get(index);
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