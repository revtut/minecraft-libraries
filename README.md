# Minecraft - Libraries

## Index
* [Introduction](#introduction)
* [Requirements](#requirements)
* [API](#api)
* [Setup MiniGame](#setup_minigame)
* [Setup Database](#setup_database)
* [Scoreboard](#scoreboard)
* [Credits](#credits)

## <a name="introduction"></a>Introduction

Nowadays libraries such as **Spigot, Bukkit** and so on, gave lots of useful APIs in order to code *Minecraft server* plugins.
However there are lots of missing functions and methods that would make a plugin development easier. In order to accomplish that, we have created a collection of useful functions to use side by side with a Minecraft server library.

## <a name="requirements"></a>Requirements
* Spigot or Bukkit
* Minecraft 1.8.*
* JAVA 8

## <a name="api"></a>API
### Generic
* Database
    * MySQL, OracleSQL and PostegreSQL
    * Methods to querie database using prepared statements
* Hash
    * BCrypt
* Structures
    * Pair
* Files
    * Read lines of a file
    * Copy and delete files / directories

### Minecraft
* Appearance
    * Create virtual anvils
    * Modify item attributes and player name tags
    * Send cameras to players
    * Particle Effects
* Entities
    * Change entity behaviors on the fly
    * Custom entity goals
* Guns
    * Realistic guns
    * Custom bullets
    * Precise shot type (head shot, knee shot, etc)
    * Events
        * Fire, Hit and Reload
* Maths
    * Get helix, tornado, sphere, circle list of locations
    * Rotate vectors
    * Check vectors intersections
    * Random lists
    * Convert text to JSON format
    * Time converters
    * String and list converters
* Network
    * Bungeecord handler
    * Message received event
* Scoreboard
    * Custom scoreboard
    * Static, Blank and Scrolling scoreboard labels
* Text
    * Multi-language methods
    * Send custom tab player, action bar, titles and subtitles
    * Checks for bad words, advertisements, caps lock and emoji
    * Scroll's such as text and color
* Utils
    * Bypass respawn screen
    * Reflection methods
    * Load / unload world sync and async
    * Change falling block damage

### Mini-Games
* Achievements
* Arena
    * Solo arena (all vs all)
    * Team arena (team vs team)
    * Locations of the arena
    * Custom arena flags for events
    * Game session for each round
* Classes
* Events
    * Arena
        * Load
        * Block break and place
        * Bucket fill and empty
    * Player
        * Catch, throw item
        * Cross arena border
        * Damage, friendly fire, die and hunger
        * Interaction, talk, jump, move
        * Join, spectate, leave arena and / or Team
    * Session
        * Start and finish
        * Timer tick and expire
        * Switch state
* Player
    * Data of a player
* Statistics
* Teams
* Utils
    * Custom colors

## <a name="setup_minigame"></a>Setup Minigame

In order to use this minigame API first you have to register your minigame. You need to have a folder where you keep all the *sample worlds* for being loaded on the fly.
```java
GameController gameController = GameAPI.getInstance().registerGame(plugin, worldsFolder);
```
this will return a game controller which will have all the methods needed to run the minigame.
After registering it you can create your arenas
```java
ArenaSolo arenaSolo = (ArenaSolo) gameController.createArena(ArenaType.SOLO);
ArenaTeam arenaTeam = (ArenaTeam) gameController.createArena(ArenaType.TEAM);
```
Now you should initialize the locations of the arena. We have created an [example file](https://gitlab.com/RevTut/minecraft-sky-wars/blob/games_api_integration/src/resources/location.yml) of the locations.
```java
GameSession gameSession = new GameSession(arena, minPlayers, maxPlayers); // You should create a game session per round
arena.initialize(...);
session.update(GameState.LOBBY, maximumTime);
```
We have also added the possibility to set the arena flags!
```java
arena.updateFlag(ArenaFlag.BLOCK_BREAK, false); // False means can't break blocks, true would mean the opposite
arena.updateFlag(Arena.HUNGER, true);
arena.updateFlag(...);
```
Thats it, now you just have to register the custom events you need such as join arena, leave arena, cross border, timer tick, timer expire, etc...

## <a name="setup_database"></a>Setup Database

Those who want more complex minigames can use databases also included here.
```java
// Database Types - MYSQL, ORACLE, POSTGRE
Database database = Database.createDatabase(DatabaseType.MYSQL, hostname, port, database, username, password);
```
Now you have got a database so lets connect to it!
```java
database.connect();
```
Do you want to query the database? That is as simple as this
```java
String query = "SELECT * FROM Players WHERE Players.UUID = ?"; // Runs on prepared statements
List<Object> parameters = new ArrayList();
parameters.add(player.getUuid());

ResultSet result = database.executeQuery(query, parameters);
while(result.next()) {
    (...)
}
```

Also insert updates?
```java
String update = "INSERT INTO Players(UUID, Name) VALUES (?, ?)";
List<Object> parameters = new ArrayList();
parameters.add(player.getUuid()); // Parameters must be sorted accordingly with the statement
parameters.add(player.getName());

int numRowsUpdated = database.executeUpdate(update, parameters);
```

## <a name="scoreboard"></a>Scoreboard

You can create custom and epic scoreboards in only 10 seconds (the time you take to copy and paste this code).
```java
// InfoBoard is the custom class for scoreboard
double updateInterval = 5; // Update interval (in ticks) of the scoreboard dynamic labels (eg. ScrollingLabel). Negative value to disable this automatic update.
InfoBoard infoBoard = new InfoBoard(updateInterval);

// Title
infoBoard.setTitle(new ScrollingLabel(new ColorScroller("MINI GAME", 1, ChatColor.AQUA, ChatColor.YELLOW, ChatColor.GOLD))); // Color scroller on the title

// Body
infoBoard.addLabel(new BlankLabel(5)); // 5 is the position on the scoreboard
infoBoard.addLabel("vip", new ScrollingLabel(new TextScroller("§fBecome §6VIP §fat our store! | ", 15, 4)); // 15 is the length of the scroller text. "vip" is the ID of the label
infoBoard.addLabel(new BlankLabel(3));

// Footer
infoBoard.addLabel(new StaticLabel("separator", "§8---------------", 2);
infoBoard.addLabel(new StaticLabel("", "§fCheck us out", 1)); // You can use empty strings for ID
infoBoard.addLabel(new StaticLabel("website", "§6www.mywebsite.com")), 0;

// Send to a player
infoBoard.send(player);
```

Well there you go! You have a gorgeous scoreboard with no effort at all!

## <a name="credits"></a>Credits

This plugin is being developed by [João Silva](https://gitlab.com/u/joaosilva2095). Some of the methods were adapted from existing ones that may be found either on [Bukkit](http://bukkit.org/forums/) or [Spigot](https://www.spigotmc.org/forums/) forum.
