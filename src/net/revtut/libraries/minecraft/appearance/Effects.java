package net.revtut.libraries.minecraft.appearance;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import net.revtut.libraries.Libraries;
import net.revtut.libraries.minecraft.maths.*;
import org.bukkit.*;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.List;

/**
 * Appearance Library.
 *
 * <P>A library with methods related to game appearance.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public final class Effects {

    /**
     * Constructor of Effects
     */
    private Effects() {}

    /**
     * Colors available
     */
    private static final Color[] colors = new Color[] {
            Color.AQUA, Color.BLACK, Color.BLUE, Color.FUCHSIA, Color.GRAY,
            Color.GREEN, Color.LIME, Color.MAROON, Color.NAVY, Color.OLIVE,
            Color.ORANGE, Color.PURPLE, Color.RED, Color.SILVER, Color.TEAL,
            Color.WHITE, Color.YELLOW
    };

    /**
     * Launch firework on player
     *
     * @param player player to launch firework
     * @param amount amount of fireworks to launch
     * @param delay  delay between each firework
     */
    public static void launchFirework(final Player player, final int amount, final int delay) {
        launchFirework(player.getLocation(), amount, delay);
    }

    /**
     * Launch firework on a location
     *
     * @param location location to launch firework
     * @param amount amount of fireworks to launch
     * @param delay  delay between each firework
     */
    public static void launchFirework(final Location location, final int amount, final int delay) {
        final Libraries plugin = Libraries.getInstance();
        for (int i = 0; i < amount; i++) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                final Firework firework = location.getWorld().spawn(location, Firework.class);
                final FireworkMeta fireworkMeta = firework.getFireworkMeta();

                final FireworkEffect.Type fireworkType = FireworkEffect.Type.values()[(int) (Math.random() * (FireworkEffect.Type.values().length - 1))];

                final Color color = getRandomColor();
                final Color fade = getRandomColor();

                final FireworkEffect effect = FireworkEffect.builder()
                        .flicker(Math.random() < 0.5)
                        .withColor(color)
                        .withFade(fade)
                        .with(fireworkType)
                        .trail(Math.random() < 0.5)
                        .build();

                fireworkMeta.addEffect(effect);
                fireworkMeta.setPower(1);
                firework.setFireworkMeta(fireworkMeta);
            }, delay * i);
        }
    }

    /**
     * Play a circle particle effect at a location
     * @param center center location to be played the effect
     * @param slope slope of the circle
     * @param radius radius of the circle
     * @param numberPoints number of points of the circle
     */
    public static void circleParticleEffect(final Location center, final double slope, final double radius, final int numberPoints) {
        circleParticleEffect(center, slope, radius, numberPoints, getRandomParticle());
    }

    /**
     * Play a circle particle effect at a location
     * @param center center location to be played the effect
     * @param slope slope of the circle
     * @param radius radius of the circle
     * @param numberPoints number of points of the circle
     * @param enumParticle particle to be played
     */
    public static void circleParticleEffect(final Location center, final double slope, final double radius, final int numberPoints, final EnumParticle enumParticle) {
        final List<Location> circleLocations = Maths.getCircle(center, 0, slope, radius, numberPoints, net.revtut.libraries.minecraft.maths.Rotation.CLOCKWISE);
        for (final Location playLocation : circleLocations) {
            final PacketPlayOutWorldParticles particlePacket = new PacketPlayOutWorldParticles(enumParticle, false, (float) playLocation.getX(), (float) playLocation.getY(), (float) playLocation.getZ(), 0f, 0f, 0f, 0f, 1);
            Packets.sendParticle(playLocation, particlePacket);
        }
    }

    /**
     * Play a helix particle effect at a location
     * @param center center location to be played the effect
     * @param height height of the helix
     * @param radius radius of the circle
     * @param numberPoints number of points of the helix
     * @param numberCircles number of circles of the helix
     */
    public static void helixParticleEffect(final Location center, final double height, final double radius, final int numberPoints, final int numberCircles) {
        helixParticleEffect(center, height, radius, numberPoints, numberCircles, getRandomParticle());
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
    public static void helixParticleEffect(final Location center, final double height, final double radius, final int numberPoints, final int numberCircles, final EnumParticle enumParticle) {
        final List<Location> helixLocations = Maths.getHelix(center, height, 0, radius, numberPoints, numberCircles, net.revtut.libraries.minecraft.maths.Rotation.CLOCKWISE);
        for (final Location playLocation : helixLocations) {
            final PacketPlayOutWorldParticles particlePacket = new PacketPlayOutWorldParticles(enumParticle, false, (float) playLocation.getX(), (float) playLocation.getY(), (float) playLocation.getZ(), 0f, 0f, 0f, 0f, 1);
            Packets.sendParticle(playLocation, particlePacket);
        }
    }

    /**
     * Play a trail particle effect from a location to another one
     * @param start start location to be played the effect
     * @param end end location to be played the effect
     * @param numberPoints number of points of the helix
     */
    public static void trailParticleEffect(final Location start, final Location end, final int numberPoints) {
        trailParticleEffect(start, end, numberPoints, getRandomParticle());
    }

    /**
     * Play a trail particle effect from a location to another one
     * @param start start location to be played the effect
     * @param end end location to be played the effect
     * @param numberPoints number of points of the helix
     * @param enumParticle particle to be played
     */
    public static void trailParticleEffect(final Location start, final Location end, final int numberPoints, final EnumParticle enumParticle) {
        final double incrementX = (end.getX() - start.getX()) / numberPoints;
        final double incrementY = (end.getY() - start.getY()) / numberPoints;
        final double incrementZ = (end.getZ() - start.getZ()) / numberPoints;

        final Location location = new Location(start.getWorld(), start.getX(), start.getY(), start.getZ());
        while(Math.abs(location.getX()) <= Math.abs(end.getX()) && Math.abs(location.getY()) <= Math.abs(end.getY()) && Math.abs(location.getZ()) <= Math.abs(end.getZ())) {
            final PacketPlayOutWorldParticles particlePacket = new PacketPlayOutWorldParticles(enumParticle, false, (float) location.getX(), (float) location.getY(), (float) location.getZ(), 0f, 0f, 0f, 0f, 1);
            Packets.sendParticle(location, particlePacket);

            // Update coordinates
            location.setX(location.getX() + incrementX);
            location.setY(location.getY() + incrementY);
            location.setZ(location.getZ() + incrementZ);
        }
    }

    /**
     * Get a random chat color
     * @return random chat color
     */
    public static ChatColor getRandomChatColor() {
        return ChatColor.values()[(int) (Math.random() * (ChatColor.values().length - 7))];
    }

    /**
     * Get a random color
     * @return random color
     */
    public static Color getRandomColor() {
        return colors[(int) (Math.random() * (colors.length - 1))];
    }

    /**
     * Get a random particle
     * @return particle effect
     */
    private static EnumParticle getRandomParticle() {
        final EnumParticle[] enumParticles = EnumParticle.values();

        final int particleValue = (int) (Math.random() * enumParticles.length) + 1;

        return enumParticles[particleValue];
    }
}
