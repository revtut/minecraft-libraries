package net.revtut.libraries.minecraft.appearance;

import net.minecraft.server.v1_8_R3.*;
import net.revtut.libraries.Libraries;
import net.revtut.libraries.minecraft.maths.AlgebraAPI;
import net.revtut.libraries.minecraft.maths.ConvertersAPI;
import net.revtut.libraries.minecraft.utils.Reflection;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

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

    /*#####################
        ACTION BAR METHODS
     #####################*/

    /**
     * Send a action bar to a player.
     * @param player player to send the action bar
     * @param message  message to be sent
     * @param stay time that the message should stay
     */
    public static void sendActionBar(final Player player, final String message, final int stay) {
        for(int i = 0; i < stay; i++) {
            Bukkit.getScheduler().runTaskLater(Libraries.getInstance(), () -> Bukkit.getScheduler().runTaskAsynchronously(Libraries.getInstance(), () -> {
                final IChatBaseComponent actionMessage = IChatBaseComponent.ChatSerializer.a(ConvertersAPI.convertToJSON(message));
                final PacketPlayOutChat ppoc = new PacketPlayOutChat(actionMessage, (byte) 2);
                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(ppoc);
            }), i * 20);
        }
    }

    /*###################
        TAB LIST METHODS
     ####################*/

    /**
     * Send the title of the tab list
     * @param player player to be set
     * @param title title of the tab list
     */
    public static void sendTabTitle(final Player player, final String title) {
        Bukkit.getScheduler().runTaskAsynchronously(Libraries.getInstance(), () -> {
            final IChatBaseComponent tabTitle = IChatBaseComponent.ChatSerializer.a(ConvertersAPI.convertToJSON(title));
            final PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();

            try {
                final Field headerField = Reflection.getField(packet.getClass(), "a");;
                if(headerField == null)
                    return;
                headerField.set(packet, tabTitle);
                headerField.setAccessible(!headerField.isAccessible());
            } catch (final Exception e) {
                e.printStackTrace();
                return;
            }
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        });
    }

    /**
     * Send the footer of the tab list
     * @param player player to be set
     * @param footer footer of the tab list
     */
    public static void sendTabFooter(final Player player, final String footer) {
        Bukkit.getScheduler().runTaskAsynchronously(Libraries.getInstance(), () -> {
            final IChatBaseComponent tabFooter = IChatBaseComponent.ChatSerializer.a(ConvertersAPI.convertToJSON(footer));
            final PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();

            try {
                final Field footerField = Reflection.getField(packet.getClass(), "b");
                if(footerField == null)
                    return;
                footerField.set(packet, tabFooter);
                footerField.setAccessible(false);
            } catch (final Exception e) {
                e.printStackTrace();
                return;
            }
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        });
    }

    /**
     * Send the tab list of a player.
     * @param player player to send the tab
     * @param title tab title
     * @param footer tab foot
     */
    public static void sendTab(final Player player, final String title, final String footer) {
        sendTabTitle(player, title);
        sendTabFooter(player, footer);
    }

    /*################
        TITLE METHODS
     #################*/

    /**
     * Send a title to a player.
     * @param player player to send the title
     * @param title title to send
     */
    public static void sendTitle(final Player player, final String title) {
        Bukkit.getScheduler().runTaskAsynchronously(Libraries.getInstance(), () -> {
            final IChatBaseComponent titleSerializer = IChatBaseComponent.ChatSerializer.a(ConvertersAPI.convertToJSON(title));
            final PacketPlayOutTitle packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleSerializer);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        });
    }

    /**
     * Send a subtitle to a player.
     * @param player player to send the subtitle
     * @param subtitle subtitle to send
     */
    public static void sendSubtitle(final Player player, final String subtitle) {
        Bukkit.getScheduler().runTaskAsynchronously(Libraries.getInstance(), () -> {
            final IChatBaseComponent subTitleSerializer = IChatBaseComponent.ChatSerializer.a(ConvertersAPI.convertToJSON(subtitle));
            final PacketPlayOutTitle packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, subTitleSerializer);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        });
    }

    /**
     * Send the title times.
     * @param player player to update the times
     * @param fadeIn time the title should take to fade in
     * @param stay time the title should stay on screen
     * @param fadeOut time the title should take to fade out
     */
    public static void sendTitleTimes(final Player player, final int fadeIn, final int stay, final int fadeOut) {
        Bukkit.getScheduler().runTaskAsynchronously(Libraries.getInstance(), () -> {
            final PacketPlayOutTitle packet = new PacketPlayOutTitle(fadeIn, stay, fadeOut);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        });
    }

    /**
     * Reset the players timing, title, subtitle.
     * @param player player to be reset
     */
    public static void resetTitleTimes(final Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(Libraries.getInstance(), () -> {
            final PacketPlayOutTitle packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.RESET, null);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        });
    }

    /**
     * Clear the players title.
     * @param player player to be cleared
     */
    public static void clearTitle(final Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(Libraries.getInstance(), () -> {
            final PacketPlayOutTitle packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.CLEAR, null);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        });
    }
}
