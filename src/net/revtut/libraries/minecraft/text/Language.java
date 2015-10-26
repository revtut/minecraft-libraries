package net.revtut.libraries.minecraft.text;

import net.revtut.libraries.minecraft.utils.Reflection;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.logging.Level;

/**
 * Languages Library.
 *
 * <P>Methods related to locales.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public final class Language {

    /**
     * Get the locale of a player using reflection
     * @param player player to get the language
     * @return language of the player
     */
    public static Locale getLocaleReflection(final Player player) {
        try {
            final Method getHandle = Reflection.getMethod(player.getClass(), "getHandle");
            if (getHandle == null) {
                Bukkit.getLogger().log(Level.SEVERE, "'getHandle' method does not exist on player class.");
                return null;
            }

            final Object playerMethod = getHandle.invoke(player, (Object[]) null);
            if (playerMethod == null) {
                Bukkit.getLogger().log(Level.SEVERE, "'getHandle' invocation failed.");
                return null;
            }

            final Field field = Reflection.getField(playerMethod.getClass(), "locale");
            if (field == null) {
                Bukkit.getLogger().log(Level.SEVERE, "'locale' field not found on player class.");
                return null;
            }

            final String lang = (String) field.get(playerMethod);
            field.setAccessible(false);

            return getByCode(lang);
        } catch (final Throwable t) {
            Bukkit.getLogger().log(Level.WARNING, "Error while getting player locale!");
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
    public static Locale getLocale(final Player player) {
        return getByCode(player.spigot().getLocale());
    }

    /**
     * Get a language by it code
     *
     * @param code code of the language
     * @return language with that code
     */
    public static Locale getByCode(final String code) {
        for (final Locale locale : Locale.values())
            if (locale.getCode().equalsIgnoreCase(code))
                return locale;
        return null;
    }

    /**
     * Get a language by it name
     *
     * @param name name of the language
     * @return language with that name
     */
    public static Locale getByName(final String name) {
        for (final Locale locale : Locale.values())
            if (locale.getName().equalsIgnoreCase(name))
                return locale;
        return null;
    }

    /**
     * Locale Enum.
     *
     * <P>All the languages available in Minecraft.</P>
     *
     * @author Joao Silva
     * @version 1.0
     */
    public enum Locale {

        /**
         * American locale
         */
        AMERICAN("American", "en_US"),

        /**
         * Afrikaans locale
         */
        AFRIKAANS("Afrikaans", "af_ZA"),

        /**
         * Arabic locale
         */
        ARABIC("العربية", "ar_SA"),

        /**
         * Argentino Spanish locale
         */
        ARGENTINEAN_SPANISH("Español Argentino", "es_AR"),

        /**
         * Armenian locale
         */
        ARMENIAN("Հայերեն", "hy_AM"),

        /**
         * Australian English locale
         */
        AUSTRALIAN_ENGLISH("Australian English", "en_AU"),

        /**
         * Bulgarian locale
         */
        BULGARIAN("Bulgarian", "bg_BG"),

        /**
         * Canadan English locale
         */
        CANADIAN_ENGLISH("Canadian English", "en_CA"),

        /**
         * Spanish Catalan locale
         */
        CATALAN("Català", "ca_ES"),

        /**
         * Croatian locale
         */
        CROATIAN("Hrvatski", "hr_HR"),

        /**
         * Cymraeg locale
         */
        CYMRAEG("Cymraeg", "cy_GB"),

        /**
         * Czech locale
         */
        CZECH("Čeština", "cs_CZ"),

        /**
         * Danish locale
         */
        DANISH("Dansk", "da_DK"),

        /**
         * Dutch locale
         */
        DUTCH("Nederlands", "nl_NL"),

        /**
         * English locale
         */
        ENGLISH("English", "en_GB"),

        /**
         * Esperanto locale
         */
        ESPERANTO("Esperanto", "eo_EO"),

        /**
         * Estonian locale
         */
        ESTONIAN("Eesti", "et_EE"),

        /**
         * Euskara locale
         */
        EUSKARA("Euskara", "eu_ES"),

        /**
         * Finnish locale
         */
        FINNISH("Suomi", "fi_FI"),

        /**
         * French locale
         */
        FRENCH("Français", "fr_FR"),

        /**
         * Canada French locale
         */
        FRENCH_CA("Français", "fr_CA"),

        /**
         * Gaeilge locale
         */
        GAEILGE("Gaeilge", "ga_IE"),

        /**
         * Galician Spanish locale
         */
        GALICIAN("Galego", "gl_ES"),

        /**
         * Georgian locale
         */
        GEORGIAN("Georgian", "ka_GE"),

        /**
         * German locale
         */
        GERMAN("Deutsch", "de_DE"),

        /**
         * Greek locale
         */
        GREEK("Ελληνικά", "el_GR"),

        /**
         * Hebrew locale
         */
        HEBREW("עברית", "he_IL"),

        /**
         * Hungarian locale
         */
        HUNGARIAN("Magyar", "hu_HU"),


        /**
         * Icelandic locale
         */
        ICELANDIC("Icelandic", "is_IS"),

        /**
         * Indian locale
         */
        INDIAN("Indian", "hi_IN"),

        /**
         * Indonesia locale
         */
        INDONESIA("Bahasa Indonesia", "id_ID"),

        /**
         * Italian locale
         */
        ITALIAN("Italiano", "it_IT"),

        /**
         * Japanese locale
         */
        JAPANESE("日本語", "ja_JP"),

        /**
         * Kernewek locale
         */
        KERNEWEK("Kernewek", "kw_GB"),

        /**
         * Korean locale
         */
        KOREAN("한국어", "ko_KR"),

        /**
         * Kyrgyzstan locale
         */
        KYRGYZSTAN("Kyrgyzstan", "ky_KG"),

        /**
         * Letzebuergesch locale
         */
        LETZEBUERGESCH("Lëtzebuergesch", "lb_LU"),

        /**
         * Latin locale
         */
        LINGUA_LATINA("Lingua latina", "la_LA"),

        /**
         * Lithuanian locale
         */
        LITHUANIAN("Lietuvių", "lt_LT"),

        /**
         * Latvian locale
         */
        LATVIAN("Latviešu", "lv_LV"),

        /**
         * Malaysia locale
         */
        MALAY_MY("Bahasa Melayu", "ms_MY"),

        /**
         * Malti locale
         */
        MALTI("Malti", "mt_MT"),

        /**
         * Mexican Spanish locale
         */
        MEXICO_SPANISH("Español México", "es_MX"),

        /**
         * New Zealand locale
         */
        NEW_ZEALAND("Bahasa Melayu", "mi_NZ"),

        /**
         * Norway locale
         */
        NORWAY("Norsk", "nb_NO"),

        /**
         * Norway locale
         */
        NORWAY1("Norway", "nn_NO"),

        /**
         * Norwegian locale
         */
        NORWEGIAN("Norwegian", "no_NO"),

        /**
         * Occitan French locale
         */
        OCCITAN("Occitan", "oc_FR"),

        /**
         * Persian locale
         */
        PERSIAN("زبان انگلیسی", "fa_IR"),

        /**
         * Pirate English locale
         */
        PIRATE_SPEAK("Pirate Speak", "en_PT"),

        /**
         * Polish locale
         */
        POLISH("Polski", "pl_PL"),

        /**
         * Brazil Portuguese locale
         */
        PORTUGUESE_BR("Português", "pt_BR"),

        /**
         * Portuguese locale
         */
        PORTUGUESE_PT("Português", "pt_PT"),

        /**
         * Quenya locale
         */
        QUENYA("Quenya", "qya_AA"),

        /**
         * Romanian locale
         */
        ROMANIAN("Română", "ro_RO"),

        /**
         * Russian locale
         */
        RUSSIAN("Russian", "ru_RU"),

        /**
         * Serbian locale
         */
        SERBIAN("Serbian", "sr_SP"),

        /**
         * Slovakia locale
         */
        SLOVAKIA("Slovakia", "sk_SK"),

        /**
         * Slovenian locale
         */
        SLOVENIAN("Slovenian", "sl_SI"),

        /**
         * Simplified Chinese locale
         */
        SIMPLIFIED_CHINESE("简体中文", "zh_CN"),

        /**
         * Spanish locale
         */
        SPANISH("Español", "es_ES"),

        /**
         * Swedish locale
         */
        SWEDISH("Svenska", "sv_SE"),

        /**
         * Tagalog locale
         */
        TAGALOG("Tagalog", "fil_PH"),

        /**
         * Thailand locale
         */
        THAILAND("ภาษาไทย", "th_TH"),

        /**
         * Traditional Chinese locale
         */
        TRADITIONAL_CHINESE("Traditional Chinese", "zh_TW"),

        /**
         * Turkish locale
         */
        TURKISH("Türkçe", "tr_TR"),

        /**
         * Ukrainian locale
         */
        UKRAINIAN("Ukrainian", "uk_UA"),

        /**
         * Uruguay Spanish locale
         */
        URUGUAY_SPANISH("Español Uruguay", "es_UY"),

        /**
         * Venezuela Spanish locale
         */
        VENEZUELA_SPANISH("Español Venezuela", "es_VE"),

        /**
         * Vietnamese locale
         */
        VIETNAMESE("Tiếng Việt", "vi_VI");

        /**
         * Name of the locale
         */
        private final String name;

        /**
         * Code of the locale
         */
        private final String code;

        /**
         * Constructor of Locale
         * @param name name of the locale
         * @param code code of the locale
         */
        Locale(final String name, final String code) {
            this.name = name;
            this.code = code;
        }

        /**
         * Get the name of the locale
         * @return name of the locale
         */
        public String getName() {
            return name;
        }

        /**
         * Get the code of the locale
         * @return code of the locale
         */
        public String getCode() {
            return code;
        }
    }
}