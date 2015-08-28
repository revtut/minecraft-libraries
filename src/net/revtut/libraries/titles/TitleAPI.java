package net.revtut.libraries.titles;

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
     * @param p     player to send the title
     * @param title json title to send
     */
    public static void sendTitle(final Player p, final String title) {
        Libraries plugin = Libraries.getInstance();
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            IChatBaseComponent titleSerializer = IChatBaseComponent.ChatSerializer.a(title);
            PacketPlayOutTitle packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleSerializer);
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
        });
    }

    /**
     * Send a subtitle to a player.
     *
     * @param p        player to send the subtitle
     * @param subtitle json subtitle to send
     */
    public static void sendSubTitle(final Player p, final String subtitle) {
        Libraries plugin = Libraries.getInstance();
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            IChatBaseComponent subTitleSerializer = IChatBaseComponent.ChatSerializer.a(subtitle);
            PacketPlayOutTitle packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, subTitleSerializer);
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
        });
    }

    /**
     * Set the title times.
     *
     * @param p       player to update the times
     * @param fadeIn  time the title should take to fade in
     * @param stay    time the title should stay on screen
     * @param fadeOut time the title should take to fade out
     */
    public static void sendTimes(final Player p, final int fadeIn, final int stay, final int fadeOut) {
        Libraries plugin = Libraries.getInstance();
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            PacketPlayOutTitle packet = new PacketPlayOutTitle(fadeIn, stay, fadeOut);
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
        });
    }

    /**
     * Reset the players timing, title, subtitle.
     *
     * @param p player to be reseted
     */
    public static void reset(final Player p) {
        Libraries plugin = Libraries.getInstance();
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            PacketPlayOutTitle packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.RESET, null);
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
        });
    }

    /**
     * Clear the players title.
     *
     * @param p player to be cleared
     */
    public static void clear(final Player p) {
        Libraries plugin = Libraries.getInstance();
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            PacketPlayOutTitle packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.CLEAR, null);
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
        });
    }
}
