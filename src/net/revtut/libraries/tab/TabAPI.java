package net.revtut.libraries.tab;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.revtut.libraries.Libraries;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

/**
 * Tab List Library.
 *
 * <P>A library with methods tab list related to.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public final class TabAPI {

    /**
     * Constructor of TabAPI
     */
    private TabAPI() {}

    /**
     * Set the tab list of a player.
     *
     * @param p      player to send the tab
     * @param title  tab title
     * @param footer tab foot
     */
    public static void setTab(final Player p, final String title, final String footer) {
        Libraries plugin = Libraries.getInstance();
        Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                IChatBaseComponent tabTitle = IChatBaseComponent.ChatSerializer.a(title);
                IChatBaseComponent tabFooter = IChatBaseComponent.ChatSerializer.a(footer);
                PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();

                try {
                    Field headerField = packet.getClass().getDeclaredField("a");
                    headerField.setAccessible(true);
                    headerField.set(packet, tabTitle);
                    headerField.setAccessible(!headerField.isAccessible());

                    Field footerField = packet.getClass().getDeclaredField("b");
                    footerField.setAccessible(true);
                    footerField.set(packet, tabFooter);
                    footerField.setAccessible(!footerField.isAccessible());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
            }
        });
    }
}