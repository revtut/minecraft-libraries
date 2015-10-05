package net.revtut.libraries.appearance;

import net.minecraft.server.v1_8_R3.PacketPlayOutCamera;
import net.revtut.libraries.Libraries;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * Camera Library.
 *
 * <P>A library with methods camera related to.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public final class CameraAPI {

    /**
     * Constructor of CameraAPI
     */
    private CameraAPI() {}

    /**
     * Send a camera to a player.
     *
     * @param player player to send the camera
     * @param alvo camera to be sent
     */
    public static void sendCamera(final Player player, final Player alvo) {
        final Libraries plugin = Libraries.getInstance();
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            final CraftPlayer craftAlvo = (CraftPlayer) alvo;
            final PacketPlayOutCamera camera = new PacketPlayOutCamera(craftAlvo.getHandle());
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(camera);
        });
    }

    /**
     * Reset the camera of a player.
     *
     * @param player player to be reseated the camera
     */
    public static void resetCamera(final Player player) {
        final Libraries plugin = Libraries.getInstance();
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            final CraftPlayer craftPlayer = (CraftPlayer) player;
            final PacketPlayOutCamera camera = new PacketPlayOutCamera(craftPlayer.getHandle());
            craftPlayer.getHandle().playerConnection.sendPacket(camera);
        });
    }
}
