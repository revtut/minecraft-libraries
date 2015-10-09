package net.revtut.libraries.minecraft.text;

import net.revtut.libraries.minecraft.utils.ReflectionAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.logging.Level;

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
    public static Language getLanguageReflection(final Player player){
        try {
            final Method getHandle = ReflectionAPI.getMethod(player.getClass(), "getHandle");
            if(getHandle == null) {
                Bukkit.getLogger().log(Level.SEVERE, "'getHandle' method does not exist on player class.");
                return null;
            }

            final Object playerMethod = getHandle.invoke(player, (Object[]) null);
            if(playerMethod == null) {
                Bukkit.getLogger().log(Level.SEVERE, "'getHandle' invocation failed.");
                return null;
            }

            final Field field = ReflectionAPI.getField(playerMethod.getClass(), "locale");
            if(field == null) {
                Bukkit.getLogger().log(Level.SEVERE, "'locale' field not found on player class.");
                return null;
            }

            final String lang = (String) field.get(playerMethod);
            field.setAccessible(false);

            return getByCode(lang);
        } catch (final Throwable t) {
            Bukkit.getLogger().log(Level.WARNING, "Error while getting player language in LanguageAPI!");
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
    public static Language getLanguage(final Player player) {
        return getByCode(player.spigot().getLocale());
    }

    /**
     * Get a language by it code
     *
     * @param code code of the language
     * @return language with that code
     */
    public static Language getByCode(final String code) {
        for (final Language language : Language.values())
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
    public static Language getByName(final String name) {
        for (final Language language : Language.values())
            if (language.getName().equalsIgnoreCase(name))
                return language;
        return null;
    }
}
