package net.revtut.libraries.particles;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import net.revtut.libraries.algebra.AlgebraAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;

import java.util.List;

/**
 * Particle API.
 *
 * <P>Help make particles with simple methods</P>
 *
 * @author João Silva
 * @version 1.0
 */
public final class ParticlesAPI {

    /**
     * Constructor of ParticlesAPI
     */
    private ParticlesAPI() {}

    /**
     * Play a circle particle effect at a location
     * @param center center location to be played the effect
     * @param slope slope of the circle
     * @param radius radius of the circle
     * @param numberPoints number of points of the circle
     * @param enumParticle particle to be played
     */
    public static void circleParticleEffect(Location center, double slope, double radius, int numberPoints, EnumParticle enumParticle) {
        List<Location> circleLocations = AlgebraAPI.getCircleCW(center, 0, slope, radius, numberPoints);
        for(int i = 0; i < circleLocations.size(); i++) {
            final Location playLocation = circleLocations.get(i);

            PacketPlayOutWorldParticles particlePacket = new PacketPlayOutWorldParticles(enumParticle, false, (float) playLocation.getX(), (float) playLocation.getY(), (float) playLocation.getZ(), 0f, 0f, 0f, 0f, 1);
            sendParticlePacket(playLocation, particlePacket);
        }
    }

    /**
     * Play a helix particle effect at a location
     * @param center center location to be played the effect
     * @param height height of the helix
     * @param radius radius of the circle
     * @param numberPoints number of points of the helix
     * @param numberCircles number of circles of the helix
     * @param enumParticle particle to be played
     */
    public static void helixParticleEffect(Location center, double height, double radius, int numberPoints, int numberCircles, EnumParticle enumParticle) {
        List<Location> helixLocations = AlgebraAPI.getHelixCW(center, height, 0, radius, numberPoints, numberCircles);
        for(int i = 0; i < helixLocations.size(); i++) {
            final Location playLocation = helixLocations.get(i);

            PacketPlayOutWorldParticles particlePacket = new PacketPlayOutWorldParticles(enumParticle, false, (float) playLocation.getX(), (float) playLocation.getY(), (float) playLocation.getZ(), 0f, 0f, 0f, 0f, 1);
            sendParticlePacket(playLocation, particlePacket);
        }
    }

    /**
     * Play a trail particle effect from a location to another one
     * @param start start location to be played the effect
     * @param end end location to be played the effect
     * @param numberPoints number of points of the helix
     * @param enumParticle particle to be played
     */
    public static void trailParticleEffect(Location start, Location end, int numberPoints, EnumParticle enumParticle) {
        double incrementX = (end.getX() - start.getX()) / numberPoints;
        double incrementY = (end.getY() - start.getY()) / numberPoints;
        double incrementZ = (end.getZ() - start.getZ()) / numberPoints;

        Location location = new Location(start.getWorld(), start.getX(), start.getY(), start.getZ());
        while(Math.abs(location.getX()) <= Math.abs(end.getX()) && Math.abs(location.getY()) <= Math.abs(end.getY()) && Math.abs(location.getZ()) <= Math.abs(end.getZ())) {
            PacketPlayOutWorldParticles particlePacket = new PacketPlayOutWorldParticles(enumParticle, false, (float) location.getX(), (float) location.getY(), (float) location.getZ(), 0f, 0f, 0f, 0f, 1);
            sendParticlePacket(location, particlePacket);

            // Update coordinates
            location.setX(location.getX() + incrementX);
            location.setY(location.getY() + incrementY);
            location.setZ(location.getZ() + incrementZ);
        }
    }

    /**
     * Make a random particle
     * @return particle effect
     */
    private static EnumParticle randomParticle() {
        EnumParticle[] enumParticles = EnumParticle.values();

        int particleValue = (int) (Math.random() * enumParticles.length) + 1;

        return enumParticles[particleValue];
    }

    /**
     * Send the particle packet to all the players
     * @param location location to play the packet
     * @param particlePacket particle packet
     */
    public static void sendParticlePacket(Location location, PacketPlayOutWorldParticles particlePacket) {
        Bukkit.getOnlinePlayers().stream()
                .filter(player -> player.getWorld().getName().equalsIgnoreCase(location.getWorld().getName()))
                .filter(player -> AlgebraAPI.distanceBetween(player.getLocation(), location) <= 20.0D)
                .forEach(player -> ((CraftPlayer) player).getHandle().playerConnection.sendPacket(particlePacket));
    }
}
