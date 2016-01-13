package net.revtut.libraries;

import net.revtut.libraries.minecraft.bukkit.network.NetworkHandler;
import net.revtut.libraries.minecraft.bukkit.network.bungee.BungeeHandler;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main class.
 *
 * <P>Libraries with methods related to Minecraft that may useful in a lot of plugins.</P>
 * <P>Contains methods about almost anything including maths, camera, world and so on.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class Libraries extends JavaPlugin {

    /**
     * Instance of Libraries
     */
    private static Libraries instance;

    /**
     * Network handler of the server
     */
    private NetworkHandler network;

    /**
     * Constructor of Libraries
     */
    public Libraries() {
        instance = this;
    }

    /**
     * Enable the plugin
     */
    @Override
    public void onEnable() { }

    /**
     * Disable the plugin
     */
    @Override
    public void onDisable() { }

    /**
     * Get the Libraries instance
     * @return instance of libraries
     */
    public static Libraries getInstance() {
        return instance;
    }

    /**
     * Get the network handler of the server
     * @return network handler of the server
     */
    public NetworkHandler getNetwork() {
        if(network == null)
            network = new BungeeHandler();
        return network;
    }
}
