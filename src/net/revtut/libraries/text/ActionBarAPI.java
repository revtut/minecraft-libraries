package net.revtut.libraries.text;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.revtut.libraries.Libraries;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * Action Bar Library.
 *
 * <P>A library with methods action bar related to.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public final class ActionBarAPI {

    /**
     * Constructor of ActionBarAPI
     */
    private ActionBarAPI() {}

    /**
     * Send a action bar to a player.
     *
     * @param p player to send the action bar
     * @param message  message to be sent
     * @param stay time that the message should stay
     */
    public static void sendActionBar(final Player p, final String message, final int stay) {
        Libraries plugin = Libraries.getInstance();
        for(int i = 0; i < stay; i++) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                IChatBaseComponent actionMessage = IChatBaseComponent.ChatSerializer.a(message);
                PacketPlayOutChat ppoc = new PacketPlayOutChat(actionMessage, (byte) 2);
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(ppoc);
            }), i * 20);
        }
    }
}
