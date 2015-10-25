package net.revtut.libraries.minecraft.appearance;

import net.revtut.libraries.Libraries;
import org.bukkit.*;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.Random;

/**
 * Appearance Library.
 *
 * <P>A library with methods related to game appearance.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public final class AppearanceAPI {

    /**
     * Constructor of AppearanceAPI
     */
    private AppearanceAPI() {}

    /**
     * Copy of random class
     */
    private static final Random random = new Random();

    /**
     * Launch firework on player
     *
     * @param player player to launch firework
     * @param amount amount of fireworks to launch
     * @param delay  delay between each firework
     */
    public static void launchFirework(final Player player, final int amount, final int delay) {
        launchFirework(player.getLocation(), amount, delay);
    }

    /**
     * Launch firework on a location
     *
     * @param location location to launch firework
     * @param amount amount of fireworks to launch
     * @param delay  delay between each firework
     */
    public static void launchFirework(final Location location, final int amount, final int delay) {
        final Libraries plugin = Libraries.getInstance();
        for (int i = 0; i < amount; i++) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                final Firework fw = location.getWorld().spawn(location, Firework.class);
                final FireworkMeta fm = fw.getFireworkMeta();

                final int fType = random.nextInt(5) + 1;
                final FireworkEffect.Type type;
                switch (fType) {
                    case 1:
                        type = FireworkEffect.Type.BALL;
                        break;
                    case 2:
                        type = FireworkEffect.Type.BALL_LARGE;
                        break;
                    case 3:
                        type = FireworkEffect.Type.CREEPER;
                        break;
                    case 4:
                        type = FireworkEffect.Type.STAR;
                        break;
                    case 5:
                        type = FireworkEffect.Type.BURST;
                        break;
                    default:
                        type = FireworkEffect.Type.BALL;
                        break;
                }

                final Color color = getColor(random.nextInt(16) + 1);
                final Color fade = getColor(random.nextInt(16) + 1);

                final FireworkEffect effect = FireworkEffect.builder().flicker(random.nextBoolean()).withColor(color).withFade(fade).with(type).trail(random.nextBoolean()).build();
                fm.addEffect(effect);
                fm.setPower(1);
                fw.setFireworkMeta(fm);
            }, delay * i);
        }
    }

    /**
     * Convert integer to color
     *
     * @param number integer to be converted
     * @return correspondent color
     */
    public static Color getColor(final int number) {
        switch (number) {
            case 1:
            default:
                return Color.AQUA;
            case 2:
                return Color.BLACK;
            case 3:
                return Color.BLUE;
            case 4:
                return Color.FUCHSIA;
            case 5:
                return Color.GRAY;
            case 6:
                return Color.GREEN;
            case 7:
                return Color.LIME;
            case 8:
                return Color.MAROON;
            case 9:
                return Color.NAVY;
            case 10:
                return Color.OLIVE;
            case 11:
                return Color.ORANGE;
            case 12:
                return Color.PURPLE;
            case 13:
                return Color.RED;
            case 14:
                return Color.SILVER;
            case 15:
                return Color.TEAL;
            case 16:
                return Color.WHITE;
            case 17:
                return Color.YELLOW;
        }
    }

    /**
     * Get a random chat color
     * @return random chat color
     */
    public static ChatColor getRandomChatColor() {
        return ChatColor.values()[(int) (Math.random() * (ChatColor.values().length - 7))];
    }
}
