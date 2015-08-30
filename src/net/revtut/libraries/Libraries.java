package net.revtut.libraries;

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
     * Enable the plugin
     */
    @Override
    public void onEnable() {
        instance = this;
    }

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
}
