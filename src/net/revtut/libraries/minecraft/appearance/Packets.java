package net.revtut.libraries.minecraft.appearance;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand;
import net.minecraft.server.v1_8_R3.PacketPlayOutCamera;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import net.revtut.libraries.Libraries;
import net.revtut.libraries.minecraft.maths.AlgebraAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * Packets Library.
 *
 * <P>A library with methods related to game packets.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class Packets {

    /*###################
        PARTICLE METHODS
     ####################*/

    /**
     * Send the particle packet to all the players
     * @param location location to play the packet
     * @param particlePacket particle packet
     */
    public static void sendParticle(final Location location, final PacketPlayOutWorldParticles particlePacket) {
        Bukkit.getOnlinePlayers().stream()
                .filter(player -> player.getWorld().getName().equalsIgnoreCase(location.getWorld().getName()))
                .filter(player -> AlgebraAPI.distanceBetween(player.getLocation(), location) <= 20.0D)
                .forEach(player -> ((CraftPlayer) player).getHandle().playerConnection.sendPacket(particlePacket));
    }

    /*#################
        CAMERA METHODS
     ##################*/

    /**
     * Send a camera to a player.
     * @param player player to send the camera
     * @param target camera to be sent
     */
    public static void sendCamera(final Player player, final Player target) {
        Bukkit.getScheduler().runTaskAsynchronously(Libraries.getInstance(), () -> {
            final CraftPlayer craftTarget = (CraftPlayer) target;
            final PacketPlayOutCamera camera = new PacketPlayOutCamera(craftTarget.getHandle());
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(camera);
        });
    }

    /**
     * Reset the camera of a player.
     * @param player player to be reset
     */
    public static void resetCamera(final Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(Libraries.getInstance(), () -> {
            final CraftPlayer craftPlayer = (CraftPlayer) player;
            final PacketPlayOutCamera camera = new PacketPlayOutCamera(craftPlayer.getHandle());
            craftPlayer.getHandle().playerConnection.sendPacket(camera);
        });
    }

    /*#################
        BYPASS METHODS
     ##################*/

    /**
     * Bypass player respawn screen.
     * @param player player to bypass respawn menu
     */
    public static void respawnBypass(final Player player) {
        final PacketPlayInClientCommand in = new PacketPlayInClientCommand(PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN);
        final EntityPlayer craftPlayer = ((CraftPlayer) player).getHandle();
        craftPlayer.playerConnection.a(in);
    }
}
