package net.revtut.libraries.minecraft.text;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.revtut.libraries.Libraries;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * Title Library.
 *
 * <P>A library with methods title related to.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public final class TitleAPI {

    /**
     * Constructor of TitleAPI
     */
    private TitleAPI() {}

    /**
     * Send a title to a player.
     *
     * @param player player to send the title
     * @param title  json title to send
     */
    public static void sendTitle(final Player player, final String title) {
        final Libraries plugin = Libraries.getInstance();
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            final IChatBaseComponent titleSerializer = IChatBaseComponent.ChatSerializer.a(title);
            final PacketPlayOutTitle packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleSerializer);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        });
    }

    /**
     * Send a subtitle to a player.
     *
     * @param player   player to send the subtitle
     * @param subtitle json subtitle to send
     */
    public static void sendSubTitle(final Player player, final String subtitle) {
        final Libraries plugin = Libraries.getInstance();
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            final IChatBaseComponent subTitleSerializer = IChatBaseComponent.ChatSerializer.a(subtitle);
            final PacketPlayOutTitle packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, subTitleSerializer);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        });
    }

    /**
     * Set the title times.
     *
     * @param player  player to update the times
     * @param fadeIn  time the title should take to fade in
     * @param stay    time the title should stay on screen
     * @param fadeOut time the title should take to fade out
     */
    public static void setTimes(final Player player, final int fadeIn, final int stay, final int fadeOut) {
        final Libraries plugin = Libraries.getInstance();
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            final PacketPlayOutTitle packet = new PacketPlayOutTitle(fadeIn, stay, fadeOut);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        });
    }

    /**
     * Reset the players timing, title, subtitle.
     *
     * @param player player to be reseted
     */
    public static void reset(final Player player) {
        final Libraries plugin = Libraries.getInstance();
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            final PacketPlayOutTitle packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.RESET, null);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        });
    }

    /**
     * Clear the players title.
     *
     * @param player player to be cleared
     */
    public static void clear(final Player player) {
        final Libraries plugin = Libraries.getInstance();
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            final PacketPlayOutTitle packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.CLEAR, null);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        });
    }
}
