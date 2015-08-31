package net.revtut.libraries.text;

import net.revtut.libraries.utils.ReflectionAPI;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Language Library.
 *
 * <P>A library with methods related to the languages.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public final class LanguageAPI {

    /**
     * Constructor of AppearanceAPI
     */
    private LanguageAPI() {}

    /**
     * Get the language of a player using reflection
     *
     * @param player player to get the language
     * @return language of the player
     */
    public static Language getLanguageReflection(Player player){
        try {
            Object playerMethod = ReflectionAPI.getMethod(player.getClass(), "getHandle").invoke(player, (Object[]) null);
            Field field = ReflectionAPI.getField(playerMethod.getClass(), "locale");

            String lang = (String) field.get(playerMethod);
            field.setAccessible(false);

            return getByCode(lang);
        } catch (Throwable t) {
            Logger.getLogger("Minecraft").log(Level.WARNING, "Error while getting player language in LanguageAPI!");
            t.printStackTrace();
            return null;
        }
    }

    /**
     * Get the language of a player
     *
     * @param player player to get the language
     * @return language of the player
     */
    public static Language getLanguage(Player player) {
        return getByCode(player.spigot().getLocale());
    }

    /**
     * Get a language by it code
     *
     * @param code code of the language
     * @return language with that code
     */
    public static Language getByCode(String code) {
        for (Language language : Language.values())
            if (language.getCode().equalsIgnoreCase(code))
                return language;
        return null;
    }

    /**
     * Get a language by it name
     *
     * @param name name of the language
     * @return language with that name
     */
    public static Language getByName(String name) {
        for (Language language : Language.values())
            if (language.getName().equalsIgnoreCase(name))
                return language;
        return null;
    }
}