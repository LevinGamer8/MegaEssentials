package de.megaessentialsrecode;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import de.megaessentialsrecode.commands.*;
import de.megaessentialsrecode.listeners.*;
import de.megaessentialsrecode.utils.*;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

import javax.sql.DataSource;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;


public final class MegaEssentials extends JavaPlugin implements PluginMessageListener {

    private DataSource dataSource;
    private static MegaEssentials instance;
    public static Logger logger() {
        return instance.getLogger();
    }
    private static EconomyProvider economyProvider;
    public static String name;
    public static String Prefix;
    public final static String noPerms = "§4Dazu hast du keine Rechte!";
    private final UpdateUTILS updateUTILS = new UpdateUTILS(this);
    private static ModdedSeralizer ms;
    private static EnderChestUtils enderchestUtils;
    private static DataHandler dH;

    @Override
    public void onEnable() {
        instance = this;
        loadConfig();
        name = this.getConfig().getString("plugin.name");
        if (Prefix != null) {
            Prefix = Objects.requireNonNull(this.getConfig().getString("plugin.prefix")).replace("&", "§");
        } else {
            Prefix = "§3MegaCraft§7: §r";
        }
        ConnectionPoolFactory connectionPool = new ConnectionPoolFactory(this.getConfig());
        try {
            dataSource = connectionPool.getPluginDataSource(this);
        } catch (SQLException e) {
            logger().log(Level.SEVERE, "Datenbankverbindung fehlgeschlagen", e);
            Bukkit.getPluginManager().disablePlugins();
            onDisable();
            return;
        }


        dependencyCheck();
        registerCommands();
        registerListeners();
        initMySQL();

        enderchestUtils = new EnderChestUtils(this);
        ms = new ModdedSeralizer();
        dH = new DataHandler(this);
    }


    public void dependencyCheck() {
        if (this.getConfig().getBoolean("economy.enabled")) {
            if (getServer().getPluginManager().getPlugin("Vault") == null) {
                getServer().getPluginManager().disablePlugin(this);
                logger().log(Level.INFO, "Du musst Vault installieren");
            } else {
                economyProvider = new EconomyProvider(this);
                getServer().getServicesManager().register(Economy.class, economyProvider, this, ServicePriority.Normal);
            }
        }
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") == null) {
            getServer().getPluginManager().disablePlugin(this);
            logger().log(Level.WARNING, "Du musst PlaceholderAPI installieren");
        } else {
            new PlaceholderProvider().register();
            logger().log(Level.INFO, "Hooked in 'PlaceholderAPI'");
        }
    }


    public void initMySQL() {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("CREATE TABLE IF NOT EXISTS playerdata (Name VARCHAR(64) NOT NULL, money DOUBLE, vanish BOOLEAN)");
             PreparedStatement ps1 = conn.prepareStatement("CREATE TABLE IF NOT EXISTS warps (Name VARCHAR(24) PRIMARY KEY, world VARCHAR(50), x DOUBLE DEFAULT 0.0, y DOUBLE DEFAULT 0.0, z DOUBLE DEFAULT 0.0, yaw FLOAT DEFAULT 0.0, pitch FLOAT DEFAULT 0.0, active BOOLEAN DEFAULT FALSE);\n");
             PreparedStatement ps2 = conn.prepareStatement("CREATE TABLE IF NOT EXISTS homes (id INT AUTO_INCREMENT, Name VARCHAR(64) NOT NULL, home_name VARCHAR(64), world VARCHAR(64), X DOUBLE, Y DOUBLE, Z DOUBLE, yaw FLOAT, pitch FLOAT, primary key(id))");
             PreparedStatement ps3 = conn.prepareStatement("CREATE TABLE IF NOT EXISTS enderchests (Name VARCHAR(24) NOT NULL, enderchest_data LONGTEXT NOT NULL, size int(3) NOT NULL, PRIMARY KEY (Name))");
        ) {
            ps.executeUpdate();
            ps1.executeUpdate();
            ps2.executeUpdate();
            ps3.executeUpdate();
        } catch (SQLException e) {
            logger().log(Level.SEVERE, "Keine Verbindung zur Datenbank!", e);
        }
    }



    public void registerCommands() {
        Objects.requireNonNull(getCommand("admin")).setExecutor(new admin());
        Objects.requireNonNull(getCommand("fly")).setExecutor(new fly());
        Objects.requireNonNull(getCommand("heal")).setExecutor(new heal());
        Objects.requireNonNull(getCommand("feed")).setExecutor(new feed());
        Objects.requireNonNull(getCommand("navi")).setExecutor(new navigator());
        if (this.getConfig().getBoolean("economy.enabled")) {
            Objects.requireNonNull(getCommand("balance")).setExecutor(new balance());
            Objects.requireNonNull(getCommand("balancetop")).setExecutor(new balancetop());
            Objects.requireNonNull(getCommand("money")).setExecutor(new balance());
            Objects.requireNonNull(getCommand("pay")).setExecutor(new pay());
            Objects.requireNonNull(getCommand("eco")).setExecutor(new eco());
        }
        Objects.requireNonNull(getCommand("invsee")).setExecutor(new invsee());
        Objects.requireNonNull(getCommand("enchant")).setExecutor(new enchant());
        Objects.requireNonNull(getCommand("enderchest")).setExecutor(new enderchest());
        Objects.requireNonNull(getCommand("ec")).setExecutor(new enderchest());
        Objects.requireNonNull(getCommand("gamemode")).setExecutor(new gamemode());
        Objects.requireNonNull(getCommand("gm")).setExecutor(new gamemode());
        Objects.requireNonNull(getCommand("day")).setExecutor(new day());
        Objects.requireNonNull(getCommand("night")).setExecutor(new night());
        Objects.requireNonNull(getCommand("sun")).setExecutor(new sun());
        Objects.requireNonNull(getCommand("rain")).setExecutor(new rain());
        Objects.requireNonNull(getCommand("tp")).setExecutor(new tp());
        Objects.requireNonNull(getCommand("tphere")).setExecutor(new tphere());
        Objects.requireNonNull(getCommand("tpa")).setExecutor(new tpa());
        Objects.requireNonNull(getCommand("tpahere")).setExecutor(new tpahere());
        Objects.requireNonNull(getCommand("tpaccept")).setExecutor(new tpaccept());
        Objects.requireNonNull(getCommand("tpdeny")).setExecutor(new tpdeny());
        Objects.requireNonNull(getCommand("home")).setExecutor(new home());
        Objects.requireNonNull(getCommand("repair")).setExecutor(new repair());
        Objects.requireNonNull(getCommand("ping")).setExecutor(new ping());
        Objects.requireNonNull(getCommand("chatclear")).setExecutor(new cc());
        Objects.requireNonNull(getCommand("vanish")).setExecutor(new vanish(this));
        Objects.requireNonNull(getCommand("update")).setExecutor(new update(this, updateUTILS));
        Objects.requireNonNull(getCommand("workbench")).setExecutor(new workbench());
        Objects.requireNonNull(getCommand("warp")).setExecutor(new warp());
        Objects.requireNonNull(getCommand("setwarp")).setExecutor(new setWarp());
        Objects.requireNonNull(getCommand("deletewarp")).setExecutor(new deleteWarp());
        if (this.getConfig().getBoolean("battlepass.enabled")) {
            Objects.requireNonNull(getCommand("battlepass")).setExecutor(new battlepass());
        }
        Objects.requireNonNull(getCommand("player")).setExecutor(new info());
        Objects.requireNonNull(getCommand("chatclear")).setExecutor(new cc());
        Objects.requireNonNull(getCommand("gift")).setExecutor(new gift());
        if (this.getConfig().getBoolean("spawn.enabled")) {
            Objects.requireNonNull(getCommand("spawn")).setExecutor(new spawn());
            Objects.requireNonNull(getCommand("setspawn")).setExecutor(new setSpawn(this));
        }
    }

    public void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new navi(instance), this);
        Bukkit.getPluginManager().registerEvents(new EssentialListener(this), this);
        Bukkit.getPluginManager().registerEvents(new AdminListener(), this);
        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
        if (this.getConfig().getBoolean("battlepass.enabled")) {
            Bukkit.getPluginManager().registerEvents(new BattlePassListener(this), this);
        }
        if (Bukkit.getPluginManager().getPlugin("LuckPerms") != null && this.getConfig().getBoolean("rankprefix.enabled")) {
            Bukkit.getPluginManager().registerEvents(new RankListener(), this);
        }
        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        Bukkit.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
        getServer().getMessenger().registerIncomingPluginChannel( this, "megacord:inv", this );
    }


    @Override
    public void onPluginMessageReceived(String channel, @NotNull Player player, byte[] bytes)
    {
        if ( !channel.equalsIgnoreCase( "megacord:inv" ) )
        {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput( bytes );
        String subChannel = in.readUTF();
        if ( subChannel.equalsIgnoreCase( "megacordSUB" ) )
        {
            ServerGUI serverGUI = new ServerGUI();
            player.openInventory(serverGUI.ServerGUI());
        }
    }

    public void loadConfig() {

        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }
        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            saveResource("config.yml", true);
        }
        this.saveDefaultConfig();
        if (!(this.getConfig().contains("mysql.host") && this.getConfig().contains("rankprefix.enabled"))) {
            this.getConfig().set("plugin.name", "megaessentials");
            this.getConfig().set("plugin.prefix", "&3MegaCraft&7: &r");
            this.getConfig().set("mysql.host", "localhost");
            this.getConfig().set("mysql.port", 3306);
            this.getConfig().set("mysql.database", "essentials");
            this.getConfig().set("mysql.username", "user");
            this.getConfig().set("mysql.password", "password");
            this.getConfig().set("economy.enabled", "true");
            this.getConfig().set("spawn.enabled", "true");
            this.getConfig().set("spawn.x", "1");
            this.getConfig().set("spawn.y", "1");
            this.getConfig().set("spawn.z", "1");
            this.getConfig().set("spawn.world", "world");
            this.getConfig().set("battlepass.enabled", "true");
            this.getConfig().set("rankprefix.enabled", "true");
            this.getConfig().set("deviceinfo.enabled", "true");
            this.getConfig().set("enderChestTitle.enderChestName", "§bEnderChest §7| ");
            this.getConfig().set("enderChestTitle.level5", "§5Level 5");
            this.getConfig().set("enderChestTitle.level4", "§5Level 4");
            this.getConfig().set("enderChestTitle.level3", "§5Level 3");
            this.getConfig().set("enderChestTitle.level2", "§5Level 2");
            this.getConfig().set("enderChestTitle.level1", "§5Level 1");
            this.getConfig().set("enderChestTitle.level0", "§5Level 0");
            this.saveConfig();
        }
    }

    public static void connect(Player p, String server) {
        p.sendMessage("§6Du §7wirst mit §b" + server + " §averbunden§8...");
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(server);
        p.sendPluginMessage(instance, "BungeeCord", out.toByteArray());
    }

    public static MegaEssentials getInstance() {
        return instance;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public static String getPluginName() {
        return name;
    }

    public static EconomyProvider getEconomyProvider() {
        return economyProvider;
    }
    public static ModdedSeralizer getModdedSerializer() {
        return ms;
    }
    public DataHandler getDataHandler() {
        return dH;
    }
    public static EnderChestUtils getEnderChestUtils() {
        return enderchestUtils;
    }

    @Override
    public void onDisable() {
        ConnectionPoolFactory connectionPool = new ConnectionPoolFactory(this.getConfig());
        connectionPool.shutdown();
    }
}
