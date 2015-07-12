package net.revtut.libraries.camera;

import net.minecraft.server.v1_8_R3.PacketPlayOutCamera;
import net.revtut.libraries.Libraries;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.logging.Level;
import java.util.logging.Logger;

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
     * Libraries class
     */
    public static Libraries plugin = null;

    /**
     * Send a camera to a player.
     *
     * @param player player to send the camera
     * @param alvo camera to be sent
     */
    public static void sendCamera(final Player player, final Player alvo) {
        if (null == plugin) {
            Logger.getLogger("Minecraft").log(Level.WARNING, "Libraries plugin is null inside CameraAPI!");
            return;
        }
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            CraftPlayer craftAlvo = (CraftPlayer) alvo;
            PacketPlayOutCamera camera = new PacketPlayOutCamera(craftAlvo.getHandle());
            ((CraftPlayer)alvo).getHandle().playerConnection.sendPacket(camera);
        });
    }

    /**
     * Reset the camera of a player.
     *
     * @param player player to be reseated the camera
     */
    public static void resetCamera(final Player player) {
        if (null == plugin) {
            Logger.getLogger("Minecraft").log(Level.WARNING, "Libraries plugin is null inside CameraAPI!");
            return;
        }
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            CraftPlayer craftPlayer = (CraftPlayer) player;
            PacketPlayOutCamera camera = new PacketPlayOutCamera(craftPlayer.getHandle());
            craftPlayer.getHandle().playerConnection.sendPacket(camera);
        });
    }
}
