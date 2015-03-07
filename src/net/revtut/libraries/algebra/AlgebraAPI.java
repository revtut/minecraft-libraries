package net.revtut.libraries.algebra;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Algebra Library.
 *
 * <P>A library with methods which envolve to algebra and mathematics.</P>
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
        //Clone the loc to prevent applied changes to the input loc
        location = location.clone();

        // Values of change in distance (make it relative)
        double deltaX = lookAt.getX() - location.getX();
        double deltaY = lookAt.getY() - location.getY();
        double deltaZ = lookAt.getZ() - location.getZ();

        // Set yaw
        if (deltaX != 0) {
            // Set yaw start value based on deltaX
            if (deltaX < 0)
                location.setYaw((float) (1.5 * Math.PI));
            else
                location.setYaw((float) (0.5 * Math.PI));
            location.setYaw(location.getYaw() - (float) Math.atan(deltaZ / deltaX));
        } else if (deltaZ < 0)
            location.setYaw((float) Math.PI);

        // Get the distance from deltaX/deltaZ
        double dxz = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaZ, 2));

        // Set pitch
        location.setPitch((float) -Math.atan(deltaY / dxz));

        // Set values, convert to degrees (invert the yaw since Bukkit uses a different yaw dimension format)
        location.setYaw(-location.getYaw() * 180f / (float) Math.PI);
        location.setPitch(location.getPitch() * 180f / (float) Math.PI);

        return location;
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
     * @param maxInteger maximum integer that may be generated
     * @param numberIntegers number of unique integeres to be generated
     * @return list with random integers
     */
    public static List<Integer> getUniqueRandomIntegers(int maxInteger, int numberIntegers) {
        if(maxInteger < numberIntegers)
            return null;
        List<Integer> list = new ArrayList<>();
        Random random = new Random();
        int number;
        for(int i = 0; i < numberIntegers; i++) {
            do {
                number = random.nextInt(maxInteger);
            } while(list.contains(number));
            list.add(number);
        }
        return list;
    }

    /**
     * Check if a player has hit someone
     *
     * @param player player to check if interacted
     * @return player that was damaged
     */
    public static Player hasHit(Player player) {
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

            if (hasIntersection(playerStart, playerEnd, minimum, maximum)) {
                if (hit == null || AlgebraAPI.distanceBetween(hit.getLocation(), playerPos) > AlgebraAPI.distanceBetween(target.getLocation(), playerPos)) {
                    hit = target;
                    continue;
                }
            }
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
}
