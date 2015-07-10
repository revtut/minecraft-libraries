package net.revtut.libraries;

import net.revtut.libraries.actionbar.ActionBarAPI;
import net.revtut.libraries.appearance.AppearanceAPI;
import net.revtut.libraries.camera.CameraAPI;
import net.revtut.libraries.particles.ParticlesAPI;
import net.revtut.libraries.tab.TabAPI;
import net.revtut.libraries.titles.TitleAPI;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main class.
 *
 * <P>Libraries with methods related to Minecraft that may useful in a lot of plugins.</P>
 * <P>Contains methods about almost anything including algebra, camera, world and so on.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class Libraries extends JavaPlugin {

    /**
     * Enable the plugin
     */
    @Override
    public void onEnable() {
        /* Set Main Classes */
        ActionBarAPI.plugin = this;
        AppearanceAPI.plugin = this;
        CameraAPI.plugin = this;
        ParticlesAPI.plugin = this;
        TabAPI.plugin = this;
        TitleAPI.plugin = this;
    }

    /**
     * Disable the plugin
     */
    @Override
    public void onDisable() { }

}
