package net.revtut.libraries.minecraft.bukkit.network;

import org.bukkit.entity.Player;

import java.util.List;

/**
 * Network handler
 */
public interface NetworkHandler {

    /**
     * Connect a player to a server
     * @param player player to be sent
     * @param server server to send the player
     */
    void connectPlayer(Player player,String server);

    /**
     * Send a message to the network
     * @param message message to be sent
     */
    void sendMessage(String message);

    /**
     * Resend messages on queue that were not sent
     */
    void resendMessages();

    /**
     * Get the incoming messages. It will remove those messages from the received messages deque.
     * @param channel channel to get those messages
     * @return incoming messages of that channel
     */
    List<String> getIncomingMessages(String channel);

    /**
     * Get the network type
     * @return network type
     */
    NetworkType getNetworkType();
}
