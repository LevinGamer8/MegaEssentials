package de.megaessentialsrecode;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import de.megaessentialsrecode.commands.*;
import de.megaessentialsrecode.listeners.*;
import de.megaessentialsrecode.utils.ConnectionPoolFactory;
import de.megaessentialsrecode.utils.EconomyProvider;
import de.megaessentialsrecode.utils.MoneyGiveTask;
import de.megaessentialsrecode.utils.PlaceholderProvider;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
    public static String booster;
    public ExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadExecutor();

    @Override
    public void onEnable() {
        instance = this;
        loadConfig();
        name = this.getConfig().getString("plugin.name");
        if (Prefix != null) {
            Prefix = this.getConfig().getString("plugin.prefix").replace("&", "§");
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
        initMySQL();
        registerCommands();
        registerListeners();
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



    private void checkPlayerOnlineTime() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            long onlineDuration = System.currentTimeMillis() - player.getFirstPlayed();
            long requiredDuration = 10 * 60 * 1000;

            if (onlineDuration >= requiredDuration) {
                int delay = 20 * 60 * 10;
                int period = 20 * 60 * 10;
                new MoneyGiveTask().runTaskTimer(this, delay, period);
            }
        }
    }

    public void initMySQL() {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("CREATE TABLE IF NOT EXISTS playerdata (Name VARCHAR(64) NOT NULL, money DOUBLE, vanish BOOLEAN)")
        ) {
            ps.executeUpdate();

        } catch (SQLException e) {
            logger().log(Level.SEVERE, "Keine Verbindung zur Datenbank!", e);
        }
    }



    public void registerCommands() {
        getCommand("admin").setExecutor(new admin());
        getCommand("fly").setExecutor(new fly());
        getCommand("heal").setExecutor(new heal());
        getCommand("feed").setExecutor(new feed());
        getCommand("navi").setExecutor(new navigator());
        if (this.getConfig().getBoolean("economy.enabled")) {
            getCommand("balance").setExecutor(new balance());
            getCommand("balancetop").setExecutor(new balancetop());
            getCommand("money").setExecutor(new balance());
            getCommand("pay").setExecutor(new pay());
            getCommand("eco").setExecutor(new eco());
        }
        getCommand("invsee").setExecutor(new invsee());
        getCommand("tpa").setExecutor(new tpa());
        getCommand("tpahere").setExecutor(new tpahere());
        getCommand("tpaccept").setExecutor(new tpaccept());
        getCommand("enchant").setExecutor(new enchant());
        getCommand("enderchest").setExecutor(new enderchest());
        getCommand("ec").setExecutor(new enderchest());
        getCommand("gamemode").setExecutor(new gamemode());
        getCommand("gm").setExecutor(new gamemode());
        getCommand("day").setExecutor(new day());
        getCommand("night").setExecutor(new night());
        getCommand("sun").setExecutor(new sun());
        getCommand("rain").setExecutor(new rain());
        getCommand("tp").setExecutor(new tp());
        getCommand("tphere").setExecutor(new tphere());
        getCommand("repair").setExecutor(new repair());
        getCommand("ping").setExecutor(new ping());
        getCommand("chatclear").setExecutor(new cc());
        getCommand("vanish").setExecutor(new vanish(this));
        getCommand("update").setExecutor(new update(this));
        if (this.getConfig().getBoolean("battlepass.enabled")) {
            getCommand("battlepass").setExecutor(new battlepass());
        }
        getCommand("player").setExecutor(new info());
        getCommand("chatclear").setExecutor(new cc());
        getCommand("gift").setExecutor(new gift());
        if (this.getConfig().getBoolean("spawn.enabled")) {
            getCommand("spawn").setExecutor(new spawn());
            getCommand("setspawn").setExecutor(new setSpawn(this));
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
        Bukkit.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this::onPluginMessageReceived);
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
        if (!this.getConfig().contains("mysql.host") || this.getConfig().contains("rankprefix.enabled")) {
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

    @Override
    public void onDisable() {
        ConnectionPoolFactory connectionPool = new ConnectionPoolFactory(this.getConfig());
        connectionPool.shutdown();
    }
}
