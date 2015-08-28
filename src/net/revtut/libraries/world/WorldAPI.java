package net.revtut.libraries.world;

import net.minecraft.server.v1_8_R3.*;
import net.revtut.libraries.Libraries;
import net.revtut.libraries.reflection.ReflectionAPI;
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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
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
    public static World loadWorld(String worldName) {
        // World Creator
        WorldCreator creator = new WorldCreator(worldName);
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
        for (Chunk chunk : world.getLoadedChunks()) {
            chunk.unload();
        }

        // Unload world
        boolean successfull = Bukkit.unloadWorld(world, true);
        RegionFileCache.a();

        return successfull;
    }

    /**
     * Load a new world to the server async.
     *
     * @param worldName name of the world to load
     * @return loaded world
     */
    public static World loadWorldAsync(String worldName) {
        while (alreadyLoading) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        alreadyLoading = true;
        aborted = false;
        wait = null;
        generator = null;
        ret = null;

        WorldCreator creator = new WorldCreator(worldName);
        Validate.notNull(creator, "Creator may not be null");

        String name = creator.name();
        generator = creator.generator();
        File folder = new File(getWorldContainer(), name);
        World world = getCraftServer().getWorld(name);
        net.minecraft.server.v1_8_R3.WorldType type = net.minecraft.server.v1_8_R3.WorldType.getType(creator.type().getName());
        boolean generateStructures = creator.generateStructures();

        if (world != null) {
            return world;
        }

        if ((folder.exists()) && (!folder.isDirectory())) {
            throw new IllegalArgumentException("File exists with the name '" + name + "' and isn't a folder");
        }

        if (generator == null) {
            generator = getGenerator(name);
        }

        Convertable converter = new WorldLoaderServer(getWorldContainer());
        if (converter.isConvertable(name)) {
            Bukkit.getLogger().info("Converting world '" + name + "'");
            converter.convert(name, new IProgressUpdate() {
                private long b = System.currentTimeMillis();

                public void a(String s) {
                }

                public void a(int i) {
                    if (System.currentTimeMillis() - this.b >= 1000L) {
                        this.b = System.currentTimeMillis();
                        MinecraftServer.LOGGER.info("Converting... " + i + "%");
                    }
                }

                public void c(String s) {
                }
            });
        }
        new BukkitRunnable() {
            public void run() {
                int dimension2 = 10 + getServer().worlds.size();
                boolean used = false;
                do
                    for (WorldServer server : getServer().worlds) {
                        used = server.dimension == dimension2;
                        if (used) {
                            dimension2++;
                            break;
                        }
                    }
                while (used);
                boolean hardcore = false;
                final int dimension = dimension2;
                new Thread() {
                    public void run() {
                        Object sdm = new ServerNBTManager(getWorldContainer(), name, true);
                        WorldData worlddata = ((IDataManager) sdm).getWorldData();
                        if (worlddata == null) {
                            WorldSettings worldSettings = new WorldSettings(creator.seed(), WorldSettings.EnumGamemode.getById(getCraftServer().getDefaultGameMode().getValue()), generateStructures, hardcore, type);
                            worldSettings.setGeneratorSettings(creator.generatorSettings());
                            worlddata = new WorldData(worldSettings, name);
                        }
                        worlddata.checkName(name);
                        WorldServer internal = (WorldServer) new WorldServer(getServer(), (IDataManager) sdm, worlddata, dimension, getServer().methodProfiler, creator.environment(), generator).b();
                        new BukkitRunnable() {
                            public void run() {
                                try {
                                    Field w = CraftServer.class.getDeclaredField("worlds");
                                    w.setAccessible(true);
                                    if (!((Map<String, World>) w.get(getCraftServer())).containsKey(name.toLowerCase())) {
                                        aborted = true;
                                        return;
                                    }
                                } catch (Exception e) {
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
                                            short short1 = 196;
                                            long i = System.currentTimeMillis();
                                            for (int j = -short1; j <= short1; j += 16) {
                                                for (int k = -short1; k <= short1; k += 16) {
                                                    long l = System.currentTimeMillis();

                                                    if (l < i) {
                                                        i = l;
                                                    }

                                                    if (l > i + 1000L) {
                                                        int i1 = (short1 * 2 + 1) * (short1 * 2 + 1);
                                                        int j1 = (j + short1) * (short1 * 2 + 1) + k + 1;

                                                        System.out.println("Preparing spawn area for " + name + ", " + j1 * 100 / i1 + "%");
                                                        i = l;
                                                    }

                                                    BlockPosition chunkcoordinates = internal.getSpawn();
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

                                    private net.minecraft.server.v1_8_R3.Chunk getChunkAt(ChunkProviderServer cps, int i, int j) {
                                        Runnable runnable = null;
                                        cps.unloadQueue.remove(i, j);
                                        net.minecraft.server.v1_8_R3.Chunk chunk = cps.chunks.get(LongHash.toLong(i, j));
                                        ChunkRegionLoader loader = null;
                                        try{
                                            Field f = ChunkProviderServer.class.getDeclaredField("chunkLoader");
                                            f.setAccessible(true);
                                            if ((f.get(cps) instanceof ChunkRegionLoader)) {
                                                loader = (ChunkRegionLoader)f.get(cps);
                                            }
                                        }catch(Exception e){
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
                                                } catch (InterruptedException e) {
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
                                    public net.minecraft.server.v1_8_R3.Chunk originalGetChunkAt(ChunkProviderServer cps, int i, int j) {
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
                                                    } catch (Throwable throwable) {
                                                        CrashReport crashreport = CrashReport.a(throwable, "Exception generating new chunk");
                                                        CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Chunk to be generated");

                                                        crashreportsystemdetails.a("Location", String.format("%d,%d", new Object[] { Integer.valueOf(i), Integer.valueOf(j) }));
                                                        crashreportsystemdetails.a("Position hash", Long.valueOf(LongHash.toLong(i, j)));
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

                                                Server server = cps.world.getServer();
                                                if (server != null) {
                                                    server.getPluginManager().callEvent(new ChunkLoadEvent(chunki.bukkitChunk, newChunki));
                                                }
                                            }}.runTask(Libraries.getInstance());

                                            for (int x = -2; x < 3; x++) {
                                                for (int z = -2; z < 3; z++) {
                                                    if ((x == 0) && (z == 0)) {
                                                        continue;
                                                    }
                                                    net.minecraft.server.v1_8_R3.Chunk neighbor = cps.getChunkIfLoaded(chunk.locX + x, chunk.locZ + z);
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

                                    public void loadNearby(net.minecraft.server.v1_8_R3.Chunk c, IChunkProvider ichunkprovider, IChunkProvider ichunkprovider1, int i, int j) {
                                        c.world.timings.syncChunkLoadPostTimer.startTiming();
                                        boolean flag = ichunkprovider.isChunkLoaded(i, j - 1);
                                        boolean flag1 = ichunkprovider.isChunkLoaded(i + 1, j);
                                        boolean flag2 = ichunkprovider.isChunkLoaded(i, j + 1);
                                        boolean flag3 = ichunkprovider.isChunkLoaded(i - 1, j);
                                        boolean flag4 = ichunkprovider.isChunkLoaded(i - 1, j - 1);
                                        boolean flag5 = ichunkprovider.isChunkLoaded(i + 1, j + 1);
                                        boolean flag6 = ichunkprovider.isChunkLoaded(i - 1, j + 1);
                                        boolean flag7 = ichunkprovider.isChunkLoaded(i + 1, j - 1);

                                        if ((flag1) && (flag2) && (flag5)) {
                                            if (!c.isDone())
                                                getChunkAt((ChunkProviderServer) ichunkprovider1, i, j);
                                            else {
                                                ichunkprovider.a(ichunkprovider1, c, i, j);
                                            }

                                        }

                                        if ((flag3) && (flag2) && (flag6)) {
                                            net.minecraft.server.v1_8_R3.Chunk chunk = getOrCreateChunk((ChunkProviderServer) ichunkprovider, i - 1, j);
                                            if (!chunk.isDone())
                                                getChunkAt((ChunkProviderServer) ichunkprovider1, i - 1, j);
                                            else {
                                                ichunkprovider.a(ichunkprovider1, chunk, i - 1, j);
                                            }
                                        }

                                        if ((flag) && (flag1) && (flag7)) {
                                            net.minecraft.server.v1_8_R3.Chunk chunk = getOrCreateChunk((ChunkProviderServer) ichunkprovider, i, j - 1);
                                            if (!chunk.isDone())
                                                getChunkAt((ChunkProviderServer) ichunkprovider1, i, j - 1);
                                            else {
                                                ichunkprovider.a(ichunkprovider1, chunk, i, j - 1);
                                            }
                                        }

                                        if ((flag4) && (flag) && (flag3)) {
                                            net.minecraft.server.v1_8_R3.Chunk chunk = getOrCreateChunk((ChunkProviderServer) ichunkprovider, i - 1, j - 1);
                                            if (!chunk.isDone())
                                                getChunkAt((ChunkProviderServer) ichunkprovider1, i - 1, j - 1);
                                            else {
                                                ichunkprovider.a(ichunkprovider1, chunk, i - 1, j - 1);
                                            }
                                        }

                                        c.world.timings.syncChunkLoadPostTimer.stopTiming();
                                    }
                                    public boolean a(IChunkProvider ichunkprovider, net.minecraft.server.v1_8_R3.Chunk chunk, int i, int j)
                                    {
                                        if ((ichunkprovider != null) && (ichunkprovider.a(ichunkprovider, chunk, i, j))) {
                                            net.minecraft.server.v1_8_R3.Chunk chunk1 = getOrCreateChunk((ChunkProviderServer) ichunkprovider, i, j);

                                            chunk1.e();
                                            return true;
                                        }
                                        return false;
                                    }

                                    private net.minecraft.server.v1_8_R3.Chunk getOrCreateChunk(ChunkProviderServer ip, int i, int j) {
                                        net.minecraft.server.v1_8_R3.Chunk chunk = ip.chunks.get(LongHash.toLong(i, j));

                                        chunk = chunk == null ? getChunkAt(ip, i, j) : (!ip.world.ad()) && (!ip.forceChunkLoad) ? ip.emptyChunk : chunk;

                                        if (chunk == ip.emptyChunk) return chunk;
                                        if ((i != chunk.locX) || (j != chunk.locZ)) {
                                            System.err.println("Chunk (" + chunk.locX + ", " + chunk.locZ + ") stored at  (" + i + ", " + j + ") in world '" + ip.world.getWorld().getName() + "'");
                                            System.err.println(chunk.getClass().getName());
                                            Throwable ex = new Throwable();
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
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
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
            Field container = CraftServer.class.getDeclaredField("container");
            container.setAccessible(true);
            Field settings = CraftServer.class.getDeclaredField("configuration");
            settings.setAccessible(true);
            File co = (File) container.get(getCraftServer());
            if (co == null)
                container.set(getCraftServer(), new File(((YamlConfiguration) settings.get(getCraftServer())).getString("settings.world-container", ".")));

            return (File) container.get(getCraftServer());
        } catch (Exception e) {
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
    public static ChunkGenerator getGenerator(String world) {
        try {
            Field settings = CraftServer.class.getDeclaredField("configuration");
            settings.setAccessible(true);
            ConfigurationSection section = ((YamlConfiguration) settings.get(getCraftServer())).getConfigurationSection("worlds");
            ChunkGenerator result = null;

            if (section != null) {
                section = section.getConfigurationSection(world);

                if (section != null) {
                    String name = section.getString("generator");

                    if ((name != null) && (!name.equals(""))) {
                        String[] split = name.split(":", 2);
                        String id = split.length > 1 ? split[1] : null;
                        Plugin plugin = Bukkit.getPluginManager().getPlugin(split[0]);

                        if (plugin == null)
                            Bukkit.getLogger().severe("Could not set generator for default world '" + world + "': Plugin '" + split[0] + "' does not exist");
                        else if (!plugin.isEnabled())
                            Bukkit.getLogger().severe("Could not set generator for default world '" + world + "': Plugin '" + plugin.getDescription().getFullName() + "' is not enabled yet (is it load:STARTUP?)");
                        else {
                            try {
                                result = plugin.getDefaultWorldGenerator(world, id);
                                if (result == null)
                                    Bukkit.getLogger().severe("Could not set generator for default world '" + world + "': Plugin '" + plugin.getDescription().getFullName() + "' lacks a default world generator");
                            } catch (Throwable t) {
                                plugin.getLogger().log(Level.SEVERE, "Could not set generator for default world '" + world + "': Plugin '" + plugin.getDescription().getFullName(), t);
                            }
                        }
                    }
                }
            }

            return result;
        } catch (Exception e) {
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
    public static boolean changeFallingBlockDamage(FallingBlock block, float damage, int max) {
        try {
            // Falling block
            Class classzz = ReflectionAPI.getOBCClass("entity.CraftFallingSand");
            Object fallingBlock = ReflectionAPI.getMethod(classzz, "getHandle").invoke(block);

            // Enable falling block damage
            classzz = ReflectionAPI.getNMSClass("EntityFallingBlock");
            Field field = ReflectionAPI.getField(classzz, "hurtEntities");
            field.setAccessible(true);
            field.setBoolean(fallingBlock, true);
            field.setAccessible(false);

            // Set the hurt amount of a falling block
            field = ReflectionAPI.getField(classzz, "fallHurtAmount");
            field.setAccessible(true);
            field.setFloat(fallingBlock, damage);
            field.setAccessible(false);

            // Set the maximum hurt amount of a falling block
            field = ReflectionAPI.getField(classzz, "fallHurtMax");
            field.setAccessible(true);
            field.setInt(fallingBlock, max);
            field.setAccessible(false);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Copy existing directory to new location.
     *
     * @param srcDir source of the folder to copy
     * @param trgDir target of the folder
     * @return true if successfull
     */
    public static boolean copyDirectory(final File srcDir, final File trgDir) {
        try {
            if (srcDir.isDirectory()) {
                // Check if target folder exists
                if (!trgDir.exists())
                    if(!trgDir.mkdirs())
                        return false;
                // List of files inside source directory
                String[] fList = srcDir.list();
                for (String aFList : fList) {
                    File dest = new File(trgDir, aFList);
                    File source = new File(srcDir, aFList);

                    // Copy that file / directory
                    copyDirectory(source, dest);
                }
            } else {
                // Copy the file
                // Open a file for read and write (copy)
                FileInputStream fInStream = new FileInputStream(srcDir);
                FileOutputStream fOutStream = new FileOutputStream(trgDir);
                // Read 2K at a time from the file
                byte[] buffer = new byte[2048];
                int iBytesReads;
                // In each successful read, write back to the source
                while ((iBytesReads = fInStream.read(buffer)) >= 0) {
                    fOutStream.write(buffer, 0, iBytesReads);
                }
                // Safe exit
                fInStream.close();
                fOutStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Delete directory. Sub-files and sub-directories will be deleted to.
     *
     * @param dir folder to remove
     * @return true it successfull when removing directory
     */
    public static boolean removeDirectory(final File dir) {
        try {
            if (dir.isDirectory()) {
                if (dir.listFiles() != null)
                    for (File c : dir.listFiles())
                        removeDirectory(c);
            }
            if(!dir.delete())
                Logger.getLogger("Minecraft").log(Level.WARNING, "Error while trying to delete " + dir.getName() + ".");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
