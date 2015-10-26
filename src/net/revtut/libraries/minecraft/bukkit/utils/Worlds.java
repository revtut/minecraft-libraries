package net.revtut.libraries.minecraft.bukkit.utils;

import net.minecraft.server.v1_8_R3.RegionFileCache;
import org.bukkit.*;
import org.bukkit.entity.FallingBlock;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.logging.Level;

/**
 * World Library.
 *
 * <P>Library with several methods world related to such as loadWorld, unloadWorld, copyDirectory and so on.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public final class Worlds {

    /**
     * Constructor of Worlds
     */
    private Worlds() {}

    /**
     * Load a new world to the server.
     *
     * @param worldName name of the world to load
     * @return loaded world
     */
    public static World loadWorld(final String worldName) {
        // World Creator
        final WorldCreator creator = new WorldCreator(worldName);
        creator.type(WorldType.FLAT);
        creator.generateStructures(false);
        return creator.createWorld();
    }

    /**
     * Load a new world to the server asynchronously.
     *
     * @param worldName name of the world to load
     * @return loaded world
     */
    public static World loadWorldAsync(final String worldName) {
        // World Creator
        final WorldCreator creator = new WorldCreator(worldName);
        creator.type(WorldType.FLAT);
        creator.generateStructures(false);

        // Load world async task
        final Thread loadWorld = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    creator.createWorld();
                } catch (final IllegalStateException exception) {
                } finally {
                    synchronized (this) {
                        notify();
                    }
                }
            }
        });
        loadWorld.start();

        // Wait for world
        synchronized (loadWorld) {
            try {
                loadWorld.wait();
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }

        return Bukkit.getWorld(worldName);
    }

    /**
     * Unload a world from the server.
     *
     * @param worldName name of the world to load
     * @return true if world was unloaded
     */
    public static boolean unloadWorld(final String worldName) {
        final World world = Bukkit.getWorld(worldName);
        if (world == null)
            return false;
        world.setAutoSave(false);
        world.setKeepSpawnInMemory(false);

        // Kick remaining players
        world.getPlayers().forEach(player -> player.kickPlayer("§fUps, looks like I was disconnected!"));

        // Remove all living entities
        world.getLivingEntities().forEach(org.bukkit.entity.LivingEntity::remove);

        // Remove all entities
        world.getEntities().forEach(org.bukkit.entity.Entity::remove);

        // Unload all the chunks
        for (final Chunk chunk : world.getLoadedChunks()) {
            chunk.unload();
        }

        // Unload world
        final boolean successfull = Bukkit.unloadWorld(world, true);
        RegionFileCache.a();

        return successfull;
    }

    /**
     * Change damage of falling blocks
     *
     * @param block falling block to change damage
     * @param damage amout of damage
     * @param max max damge applied
     * @return true if successfull
     */
    public static boolean changeFallingBlockDamage(final FallingBlock block, final float damage, final int max) {
        try {
            // Falling block
            Class classzz = Reflection.getOBCClass("entity.CraftFallingSand");
            final Method getHandle = Reflection.getMethod(classzz, "getHandle");
            if(getHandle == null) {
                Bukkit.getLogger().log(Level.SEVERE, "'getHandle' method does not exist on falling block class.");
                return false;
            }

            final Object fallingBlock = getHandle.invoke(block);

            // Enable falling block damage
            classzz = Reflection.getNMSClass("EntityFallingBlock");
            Field field = Reflection.getField(classzz, "hurtEntities");
            if(field == null) {
                Bukkit.getLogger().log(Level.SEVERE, "'hurtEntities' field does not exist on falling block class.");
                return false;
            }
            field.setBoolean(fallingBlock, true);
            field.setAccessible(false);

            // Set the hurt amount of a falling block
            field = Reflection.getField(classzz, "fallHurtAmount");
            if(field == null) {
                Bukkit.getLogger().log(Level.SEVERE, "'fallHurtAmount' field does not exist on falling block class.");
                return false;
            }
            field.setFloat(fallingBlock, damage);
            field.setAccessible(false);

            // Set the maximum hurt amount of a falling block
            field = Reflection.getField(classzz, "fallHurtMax");
            if(field == null) {
                Bukkit.getLogger().log(Level.SEVERE, "'fallHurtMax' field does not exist on falling block class.");
                return false;
            }
            field.setInt(fallingBlock, max);
            field.setAccessible(false);

            return true;
        } catch (final Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
