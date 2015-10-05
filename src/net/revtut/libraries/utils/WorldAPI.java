package net.revtut.libraries.utils;

import net.minecraft.server.v1_8_R3.*;
import net.revtut.libraries.Libraries;
import org.apache.commons.lang.Validate;
import org.bukkit.*;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.chunkio.ChunkIOExecutor;
import org.bukkit.craftbukkit.v1_8_R3.util.LongHash;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * World Library.
 *
 * <P>Library with several methods world related to such as loadWorld, unloadWorld, copyDirectory and so on.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public final class WorldAPI {

    /**
     * Constructor of WorldAPI
     */
    private WorldAPI() {}

    /**
     * World to be returned
     */
    private static World ret = null;

    /**
     * Flag to check if world loading was aborted
     */
    private static boolean aborted = false;

    /**
     * Flag to check if there is a world already loading
     */
    private static boolean alreadyLoading = false;

    /**
     * Chunk generator
     */
    private static ChunkGenerator generator;

    /**
     * Wait for chunk loading flag
     */
    private static net.minecraft.server.v1_8_R3.Chunk wait = null;

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
     * Load a new world to the server async.
     *
     * @param worldName name of the world to load
     * @return loaded world
     */
    public static World loadWorldAsync(final String worldName) {
        while (alreadyLoading) {
            try {
                Thread.sleep(50);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }
        alreadyLoading = true;
        aborted = false;
        wait = null;
        generator = null;
        ret = null;

        final WorldCreator creator = new WorldCreator(worldName);
        Validate.notNull(creator, "Creator may not be null");

        final String name = creator.name();
        generator = creator.generator();
        final File folder = new File(getWorldContainer(), name);
        final World world = getCraftServer().getWorld(name);
        final net.minecraft.server.v1_8_R3.WorldType type = net.minecraft.server.v1_8_R3.WorldType.getType(creator.type().getName());
        final boolean generateStructures = creator.generateStructures();

        if (world != null) {
            return world;
        }

        if ((folder.exists()) && (!folder.isDirectory())) {
            throw new IllegalArgumentException("File exists with the name '" + name + "' and isn't a folder");
        }

        if (generator == null) {
            generator = getGenerator(name);
        }

        final Convertable converter = new WorldLoaderServer(getWorldContainer());
        if (converter.isConvertable(name)) {
            Bukkit.getLogger().info("Converting world '" + name + "'");
            converter.convert(name, new IProgressUpdate() {
                private long b = System.currentTimeMillis();

                public void a(final String s) {
                }

                public void a(final int i) {
                    if (System.currentTimeMillis() - this.b >= 1000L) {
                        this.b = System.currentTimeMillis();
                        MinecraftServer.LOGGER.info("Converting... " + i + "%");
                    }
                }

                public void c(final String s) {
                }
            });
        }
        new BukkitRunnable() {
            public void run() {
                int dimension2 = 10 + getServer().worlds.size();
                boolean used = false;
                do
                    for (final WorldServer server : getServer().worlds) {
                        used = server.dimension == dimension2;
                        if (used) {
                            dimension2++;
                            break;
                        }
                    }
                while (used);
                final boolean hardcore = false;
                final int dimension = dimension2;
                new Thread() {
                    public void run() {
                        final Object sdm = new ServerNBTManager(getWorldContainer(), name, true);
                        WorldData worlddata = ((IDataManager) sdm).getWorldData();
                        if (worlddata == null) {
                            final WorldSettings worldSettings = new WorldSettings(creator.seed(), WorldSettings.EnumGamemode.getById(getCraftServer().getDefaultGameMode().getValue()), generateStructures, hardcore, type);
                            worldSettings.setGeneratorSettings(creator.generatorSettings());
                            worlddata = new WorldData(worldSettings, name);
                        }
                        worlddata.checkName(name);
                        final WorldServer internal = (WorldServer) new WorldServer(getServer(), (IDataManager) sdm, worlddata, dimension, getServer().methodProfiler, creator.environment(), generator).b();
                        new BukkitRunnable() {
                            public void run() {
                                try {
                                    final Field w = CraftServer.class.getDeclaredField("worlds");
                                    w.setAccessible(true);
                                    if (!((Map<String, World>) w.get(getCraftServer())).containsKey(name.toLowerCase())) {
                                        aborted = true;
                                        return;
                                    }
                                } catch (final Exception e) {
                                    e.printStackTrace();
                                    aborted = true;
                                    return;
                                }
                                new Thread() {
                                    public void run() {

                                        internal.scoreboard = getCraftServer().getScoreboardManager().getMainScoreboard().getHandle();
                                        internal.tracker = new EntityTracker(internal);
                                        internal.addIWorldAccess(new WorldManager(getServer(), internal));
                                        internal.worldData.setDifficulty(EnumDifficulty.EASY);
                                        internal.setSpawnFlags(true, true);
                                        getServer().worlds.add(internal);

                                        if (generator != null) {
                                            internal.getWorld().getPopulators().addAll(generator.getDefaultPopulators(internal.getWorld()));
                                        }

                                        new BukkitRunnable() {
                                            public void run() {
                                                Bukkit.getPluginManager().callEvent(new WorldInitEvent(internal.getWorld()));
                                            }
                                        }.runTask(Libraries.getInstance());
                                        System.out.print("Preparing start region for level " + (getServer().worlds.size() - 1) + " (Seed: " + internal.getSeed() + ")");
                                        if (internal.getWorld().getKeepSpawnInMemory()) {
                                            final short short1 = 196;
                                            long i = System.currentTimeMillis();
                                            for (int j = -short1; j <= short1; j += 16) {
                                                for (int k = -short1; k <= short1; k += 16) {
                                                    final long l = System.currentTimeMillis();

                                                    if (l < i) {
                                                        i = l;
                                                    }

                                                    if (l > i + 1000L) {
                                                        final int i1 = (short1 * 2 + 1) * (short1 * 2 + 1);
                                                        final int j1 = (j + short1) * (short1 * 2 + 1) + k + 1;

                                                        System.out.println("Preparing spawn area for " + name + ", " + j1 * 100 / i1 + "%");
                                                        i = l;
                                                    }

                                                    final BlockPosition chunkcoordinates = internal.getSpawn();
                                                    getChunkAt(internal.chunkProviderServer, chunkcoordinates.getX() + j >> 4, chunkcoordinates.getZ() + k >> 4);
                                                }
                                            }
                                        }
                                        new BukkitRunnable() {
                                            public void run() {
                                                Bukkit.getPluginManager().callEvent(new WorldLoadEvent(internal.getWorld()));
                                            }
                                        }.runTask(Libraries.getInstance());
                                        ret = (World) internal.getWorld();
                                    }

                                    private net.minecraft.server.v1_8_R3.Chunk getChunkAt(final ChunkProviderServer cps, final int i, final int j) {
                                        final Runnable runnable = null;
                                        cps.unloadQueue.remove(i, j);
                                        net.minecraft.server.v1_8_R3.Chunk chunk = cps.chunks.get(LongHash.toLong(i, j));
                                        ChunkRegionLoader loader = null;
                                        try{
                                            final Field f = ChunkProviderServer.class.getDeclaredField("chunkLoader");
                                            f.setAccessible(true);
                                            if ((f.get(cps) instanceof ChunkRegionLoader)) {
                                                loader = (ChunkRegionLoader)f.get(cps);
                                            }
                                        }catch(final Exception e){
                                            e.printStackTrace();
                                        }

                                        if ((chunk == null) && (loader != null) && (loader.chunkExists(cps.world, i, j))) {
                                            final ChunkRegionLoader loader1 = loader;
                                            wait = null;
                                            new BukkitRunnable(){public void run(){
                                                wait = ChunkIOExecutor.syncChunkLoad(cps.world, loader1, cps, i, j);
                                            }}.runTask(Libraries.getInstance());
                                            while(wait==null){
                                                try {
                                                    Thread.sleep(10);
                                                } catch (final InterruptedException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            chunk = wait;
                                        }
                                        else if (chunk == null) {
                                            chunk = originalGetChunkAt(cps, i, j);
                                        }

                                        if (runnable != null) {
                                            runnable.run();
                                        }

                                        return chunk;
                                    }
                                    public net.minecraft.server.v1_8_R3.Chunk originalGetChunkAt(final ChunkProviderServer cps, final int i, final int j) {
                                        cps.unloadQueue.remove(i, j);
                                        net.minecraft.server.v1_8_R3.Chunk chunk = cps.chunks.get(LongHash.toLong(i, j));
                                        boolean newChunk = false;

                                        if (chunk == null) {
                                            cps.world.timings.syncChunkLoadTimer.startTiming();
                                            chunk = cps.loadChunk(i, j);
                                            if (chunk == null) {
                                                if (cps.chunkProvider == null)
                                                    chunk = cps.emptyChunk;
                                                else {
                                                    try {
                                                        chunk = cps.chunkProvider.getOrCreateChunk(i, j);
                                                    } catch (final Throwable throwable) {
                                                        final CrashReport crashreport = CrashReport.a(throwable, "Exception generating new chunk");
                                                        final CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Chunk to be generated");

                                                        crashreportsystemdetails.a("Location", String.format("%d,%d", new Object[] {i, j}));
                                                        crashreportsystemdetails.a("Position hash", LongHash.toLong(i, j));
                                                        crashreportsystemdetails.a("Generator", cps.chunkProvider.getName());
                                                        throw new ReportedException(crashreport);
                                                    }
                                                }
                                                newChunk = true;
                                            }

                                            cps.chunks.put(LongHash.toLong(i, j), chunk);
                                            final net.minecraft.server.v1_8_R3.Chunk chunki = chunk;
                                            final boolean newChunki = newChunk;
                                            new BukkitRunnable(){public void run(){
                                                chunki.addEntities();

                                                final Server server = cps.world.getServer();
                                                if (server != null) {
                                                    server.getPluginManager().callEvent(new ChunkLoadEvent(chunki.bukkitChunk, newChunki));
                                                }
                                            }}.runTask(Libraries.getInstance());

                                            for (int x = -2; x < 3; x++) {
                                                for (int z = -2; z < 3; z++) {
                                                    if ((x == 0) && (z == 0)) {
                                                        continue;
                                                    }
                                                    final net.minecraft.server.v1_8_R3.Chunk neighbor = cps.getChunkIfLoaded(chunk.locX + x, chunk.locZ + z);
                                                    if (neighbor != null) {
                                                        neighbor.setNeighborLoaded(-x, -z);
                                                        chunk.setNeighborLoaded(x, z);
                                                    }
                                                }
                                            }

                                            loadNearby(chunk, cps, cps, i, j);
                                            cps.world.timings.syncChunkLoadTimer.stopTiming();
                                        }

                                        return chunk;
                                    }

                                    public void loadNearby(final net.minecraft.server.v1_8_R3.Chunk c, final IChunkProvider ichunkprovider, final IChunkProvider ichunkprovider1, final int i, final int j) {
                                        c.world.timings.syncChunkLoadPostTimer.startTiming();
                                        final boolean flag = ichunkprovider.isChunkLoaded(i, j - 1);
                                        final boolean flag1 = ichunkprovider.isChunkLoaded(i + 1, j);
                                        final boolean flag2 = ichunkprovider.isChunkLoaded(i, j + 1);
                                        final boolean flag3 = ichunkprovider.isChunkLoaded(i - 1, j);
                                        final boolean flag4 = ichunkprovider.isChunkLoaded(i - 1, j - 1);
                                        final boolean flag5 = ichunkprovider.isChunkLoaded(i + 1, j + 1);
                                        final boolean flag6 = ichunkprovider.isChunkLoaded(i - 1, j + 1);
                                        final boolean flag7 = ichunkprovider.isChunkLoaded(i + 1, j - 1);

                                        if ((flag1) && (flag2) && (flag5)) {
                                            if (!c.isDone())
                                                getChunkAt((ChunkProviderServer) ichunkprovider1, i, j);
                                            else {
                                                ichunkprovider.a(ichunkprovider1, c, i, j);
                                            }

                                        }

                                        if ((flag3) && (flag2) && (flag6)) {
                                            final net.minecraft.server.v1_8_R3.Chunk chunk = getOrCreateChunk((ChunkProviderServer) ichunkprovider, i - 1, j);
                                            if (!chunk.isDone())
                                                getChunkAt((ChunkProviderServer) ichunkprovider1, i - 1, j);
                                            else {
                                                ichunkprovider.a(ichunkprovider1, chunk, i - 1, j);
                                            }
                                        }

                                        if ((flag) && (flag1) && (flag7)) {
                                            final net.minecraft.server.v1_8_R3.Chunk chunk = getOrCreateChunk((ChunkProviderServer) ichunkprovider, i, j - 1);
                                            if (!chunk.isDone())
                                                getChunkAt((ChunkProviderServer) ichunkprovider1, i, j - 1);
                                            else {
                                                ichunkprovider.a(ichunkprovider1, chunk, i, j - 1);
                                            }
                                        }

                                        if ((flag4) && (flag) && (flag3)) {
                                            final net.minecraft.server.v1_8_R3.Chunk chunk = getOrCreateChunk((ChunkProviderServer) ichunkprovider, i - 1, j - 1);
                                            if (!chunk.isDone())
                                                getChunkAt((ChunkProviderServer) ichunkprovider1, i - 1, j - 1);
                                            else {
                                                ichunkprovider.a(ichunkprovider1, chunk, i - 1, j - 1);
                                            }
                                        }

                                        c.world.timings.syncChunkLoadPostTimer.stopTiming();
                                    }
                                    public boolean a(final IChunkProvider ichunkprovider, final net.minecraft.server.v1_8_R3.Chunk chunk, final int i, final int j)
                                    {
                                        if ((ichunkprovider != null) && (ichunkprovider.a(ichunkprovider, chunk, i, j))) {
                                            final net.minecraft.server.v1_8_R3.Chunk chunk1 = getOrCreateChunk((ChunkProviderServer) ichunkprovider, i, j);

                                            chunk1.e();
                                            return true;
                                        }
                                        return false;
                                    }

                                    private net.minecraft.server.v1_8_R3.Chunk getOrCreateChunk(final ChunkProviderServer ip, final int i, final int j) {
                                        net.minecraft.server.v1_8_R3.Chunk chunk = ip.chunks.get(LongHash.toLong(i, j));

                                        chunk = chunk == null ? getChunkAt(ip, i, j) : (!ip.world.ad()) && (!ip.forceChunkLoad) ? ip.emptyChunk : chunk;

                                        if (chunk == ip.emptyChunk) return chunk;
                                        if ((i != chunk.locX) || (j != chunk.locZ)) {
                                            System.err.println("Chunk (" + chunk.locX + ", " + chunk.locZ + ") stored at  (" + i + ", " + j + ") in world '" + ip.world.getWorld().getName() + "'");
                                            System.err.println(chunk.getClass().getName());
                                            final Throwable ex = new Throwable();
                                            ex.fillInStackTrace();
                                            ex.printStackTrace();
                                        }

                                        return chunk;

                                    }
                                }.start();
                            }
                        }.runTask(Libraries.getInstance());
                    }
                }.start();
            }
        }.runTask(Libraries.getInstance());

        while (ret == null && !aborted) {
            try {
                Thread.sleep(50);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            Thread.sleep(1000);
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
        alreadyLoading = false;
        return ret;
    }

    /**
     * Get the world container
     * @return world container
     */
    public static File getWorldContainer() {
        if (getServer().universe != null) {
            return getServer().universe;
        }
        try {
            final Field container = CraftServer.class.getDeclaredField("container");
            container.setAccessible(true);
            final Field settings = CraftServer.class.getDeclaredField("configuration");
            settings.setAccessible(true);
            final File co = (File) container.get(getCraftServer());
            if (co == null)
                container.set(getCraftServer(), new File(((YamlConfiguration) settings.get(getCraftServer())).getString("settings.world-container", ".")));

            return (File) container.get(getCraftServer());
        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get the minecraft server
     * @return minecraft server
     */
    public static MinecraftServer getServer() {
        return getCraftServer().getServer();
    }

    /**
     * Get the craft server
     * @return craft server
     */
    public static CraftServer getCraftServer() {
        return ((CraftServer) Bukkit.getServer());
    }

    /**
     * Get the chunk generator
     * @param world world to get chunk generator
     * @return chunk generator
     */
    public static ChunkGenerator getGenerator(final String world) {
        try {
            final Field settings = CraftServer.class.getDeclaredField("configuration");
            settings.setAccessible(true);
            ConfigurationSection section = ((YamlConfiguration) settings.get(getCraftServer())).getConfigurationSection("worlds");
            ChunkGenerator result = null;

            if (section != null) {
                section = section.getConfigurationSection(world);

                if (section != null) {
                    final String name = section.getString("generator");

                    if ((name != null) && (!name.equals(""))) {
                        final String[] split = name.split(":", 2);
                        final String id = split.length > 1 ? split[1] : null;
                        final Plugin plugin = Bukkit.getPluginManager().getPlugin(split[0]);

                        if (plugin == null)
                            Bukkit.getLogger().severe("Could not set generator for default world '" + world + "': Plugin '" + split[0] + "' does not exist");
                        else if (!plugin.isEnabled())
                            Bukkit.getLogger().severe("Could not set generator for default world '" + world + "': Plugin '" + plugin.getDescription().getFullName() + "' is not enabled yet (is it load:STARTUP?)");
                        else {
                            try {
                                result = plugin.getDefaultWorldGenerator(world, id);
                                if (result == null)
                                    Bukkit.getLogger().severe("Could not set generator for default world '" + world + "': Plugin '" + plugin.getDescription().getFullName() + "' lacks a default world generator");
                            } catch (final Throwable t) {
                                plugin.getLogger().log(Level.SEVERE, "Could not set generator for default world '" + world + "': Plugin '" + plugin.getDescription().getFullName(), t);
                            }
                        }
                    }
                }
            }

            return result;
        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
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
            Class classzz = ReflectionAPI.getOBCClass("entity.CraftFallingSand");
            final Method getHandle = ReflectionAPI.getMethod(classzz, "getHandle");
            if(getHandle == null) {
                Logger.getLogger("Minecraft").log(Level.SEVERE, "'getHandle' method does not exist on falling block class.");
                return false;
            }

            final Object fallingBlock = getHandle.invoke(block);

            // Enable falling block damage
            classzz = ReflectionAPI.getNMSClass("EntityFallingBlock");
            Field field = ReflectionAPI.getField(classzz, "hurtEntities");
            if(field == null) {
                Logger.getLogger("Minecraft").log(Level.SEVERE, "'hurtEntities' field does not exist on falling block class.");
                return false;
            }
            field.setAccessible(true);
            field.setBoolean(fallingBlock, true);
            field.setAccessible(false);

            // Set the hurt amount of a falling block
            field = ReflectionAPI.getField(classzz, "fallHurtAmount");
            if(field == null) {
                Logger.getLogger("Minecraft").log(Level.SEVERE, "'fallHurtAmount' field does not exist on falling block class.");
                return false;
            }
            field.setAccessible(true);
            field.setFloat(fallingBlock, damage);
            field.setAccessible(false);

            // Set the maximum hurt amount of a falling block
            field = ReflectionAPI.getField(classzz, "fallHurtMax");
            if(field == null) {
                Logger.getLogger("Minecraft").log(Level.SEVERE, "'fallHurtMax' field does not exist on falling block class.");
                return false;
            }
            field.setAccessible(true);
            field.setInt(fallingBlock, max);
            field.setAccessible(false);

            return true;
        } catch (final Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
