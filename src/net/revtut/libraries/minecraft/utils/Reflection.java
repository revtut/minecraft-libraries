package net.revtut.libraries.minecraft.utils;

import org.bukkit.Bukkit;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.logging.Level;

/**
 * Reflection Library.
 *
 * <P>A library with methods related to reflection.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public final class Reflection {

    /**
     * Constructor of Reflection
     */
    private Reflection() {}

    /**
     * Get version of Minecraft.
     *
     * @return version of the game
     */
    private static String getMinecraftVersion() {
        final String name = Bukkit.getServer().getClass().getPackage().getName();
        return name.substring(name.lastIndexOf('.') + 1) + ".";
    }

    /**
     * Get a NMS class given a name.
     *
     * @param className className to get the NMS class
     * @return nms class
     */
    public static Class<?> getNMSClass(final String className) {
        final String fullName = "net.minecraft.server." + getMinecraftVersion() + className;
        Class<?> clazz = null;
        try {
            clazz = Class.forName(fullName);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return clazz;
    }

    /**
     * Get a CraftBukkit class given its name.
     *
     * @param className className to get the CraftBukkit class
     * @return craftBukkit class
     */
    public static Class<?> getOBCClass(final String className) {
        final String fullName = "org.bukkit.craftbukkit." + getMinecraftVersion() + className;
        Class<?> clazz = null;
        try {
            clazz = Class.forName(fullName);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return clazz;
    }

    /**
     * Get a field from a class given its class and its name.
     *
     * @param clazz class to get the field
     * @param name  field name
     * @return field of the class
     */
    public static Field getField(final Class<?> clazz, final String name) {
        try {
            final Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            return field;
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get a method from a class.
     *
     * @param clazz class to get the method
     * @param name  method name
     * @param args  args of the method
     * @return method of the class
     */
    public static Method getMethod(final Class<?> clazz, final String name, final Class<?>... args) {
        for (final Method m : clazz.getMethods()) {
            if ((m.getName().equals(name)) && ((args.length == 0) || (classListEqual(args, m.getParameterTypes())))) {
                m.setAccessible(true);
                return m;
            }
        }
        return null;
    }

    /**
     * Get a object handle.
     *
     * @param obj object to get the handle
     * @return object handle
     */
    public static Object getHandle(final Object obj) {
        try {
            final Method getHandle = getMethod(obj.getClass(), "getHandle");
            if(getHandle == null) {
                Bukkit.getLogger().log(Level.SEVERE, "'getHandle' method does not exist on this object.");
                return null;
            }

            return getHandle.invoke(obj, new Object());
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Compare two classes
     *
     * @param l1 class one to compare
     * @param l2 class second to compare
     * @return true if they are equal
     */
    private static boolean classListEqual(final Class<?>[] l1, final Class<?>[] l2) {
        boolean equal = true;
        if (l1.length != l2.length) {
            return false;
        }
        for (int i = 0; i < l1.length; i++) {
            if (l1[i] != l2[i]) {
                equal = false;
                break;
            }
        }
        return equal;
    }
}
