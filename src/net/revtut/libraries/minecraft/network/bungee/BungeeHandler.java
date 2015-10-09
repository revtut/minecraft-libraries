package net.revtut.libraries.minecraft.network.bungee;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.revtut.libraries.Libraries;
import net.revtut.libraries.generic.structures.Pair;
import net.revtut.libraries.minecraft.network.NetworkHandler;
import net.revtut.libraries.minecraft.network.NetworkType;
import net.revtut.libraries.minecraft.network.events.MessageReceivedEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.util.*;

/**
 * BungeeCord Network
 */
public class BungeeHandler implements NetworkHandler, PluginMessageListener {

    /**
     * Dequeue of messages to be sent
     */
    private final Deque<String> sendMessages;

    /**
     * Deque with received messages (channel => message)
     */
    private final Deque<Pair<String, String>> receivedMessages;

    /**
     * Constructor of BungeeHandler
     */
    public BungeeHandler() {
        this.sendMessages = new ArrayDeque<>();
        this.receivedMessages = new ArrayDeque<>();

        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(Libraries.getInstance(), "BungeeCord");
        Bukkit.getServer().getMessenger().registerIncomingPluginChannel(Libraries.getInstance(), "BungeeCord", this);

        Bukkit.getScheduler().runTaskTimer(Libraries.getInstance(), this::resendMessages, 20L, 20L);
    }

    /**
     * Send a player to a server
     * @param player player to be sent
     * @param server server to send the player
     */
    @Override
    public void connectPlayer(final Player player, final String server) {
        final ByteArrayDataOutput out = ByteStreams.newDataOutput();

        out.writeUTF("Connect");
        out.writeUTF(server);
        out.writeUTF(player.getName());

        sendMessage(out.toString());
    }

    /**
     * Send a message to the network
     * @param message message to be sent
     */
    @Override
    public void sendMessage(final String message) {
        final Optional<? extends Player> any = Bukkit.getOnlinePlayers().stream().findAny();
        if (any.isPresent())
            any.get().sendPluginMessage(Libraries.getInstance(), "BungeeCord", message.getBytes());
        else
            sendMessages.add(message);
    }

    /**
     * Resend messages on queue that were not sent
     */
    @Override
    public void resendMessages() {
        if(sendMessages.size() < 1)
            return;

        final Optional<? extends Player> any = Bukkit.getOnlinePlayers().stream().findAny();
        if (any.isPresent())
            for(final String message : sendMessages)
                any.get().sendPluginMessage(Libraries.getInstance(), "BungeeCord", message.getBytes());
    }

    /**
     * Get the incoming messages. It will remove those messages from the received messages deque.
     * @param channel channel to get those messages
     * @return incoming messages of that channel
     */
    public List<String> getIncomingMessages(final String channel) {
        final List<String> messages = new ArrayList<>();

        for (final Pair<String, String> receivedMessage : new ArrayDeque<>(receivedMessages)) {
            if (!receivedMessage.getFirst().equalsIgnoreCase(channel))
                continue;

            messages.add(receivedMessage.getSecond());
            receivedMessages.remove(receivedMessage);
        }

        return messages;
    }

    /**
     * Get the network type
     * @return network type
     */
    @Override
    public NetworkType getNetworkType() {
        return NetworkType.BUNGEE;
    }

    /**
     * Controls the message received event
     * @param channel channel of the message
     * @param player player that sent the message
     * @param bytes message received
     */
    @Override
    public void onPluginMessageReceived(final String channel, final Player player, final byte[] bytes) {
        final String message = ByteStreams.newDataInput(bytes).readUTF();

        // Call event
        final MessageReceivedEvent event = new MessageReceivedEvent(message, channel);
        Bukkit.getPluginManager().callEvent(event);

        if(!event.isAccepted())
            receivedMessages.add(new Pair<>(channel, message));

    }
}
