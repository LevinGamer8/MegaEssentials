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
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public final class MegaEssentials extends JavaPlugin{

    private DataBase dataBase;
    public static Plugin instance;
    public static Logger logger;
    private static EconomyProvider economyProvider;
    public static String name;
    public final static String Prefix = "§3MegaCraft§7: §r";
    public final static String noPerms = "§4Dazu hast du keine Rechte!";

    @Override
    public void onEnable() {
        instance = this;
        name = this.getConfig().getString("plugin.name");
        logger = getLogger();
        loadConfig();
        dependencyCheck();
        initMysql();
        createTables();
        registerCommands();
        registerListeners();

    }


    public void dependencyCheck() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            getServer().getPluginManager().disablePlugin(this);
            logger.log(Level.INFO, "Du musst Vault installieren");
        } else {
            economyProvider = new EconomyProvider(this);
            getServer().getServicesManager().register(Economy.class, economyProvider, this, ServicePriority.Normal);
        }
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") == null) {
            getServer().getPluginManager().disablePlugin(this);
            logger.log(Level.WARNING, "Du musst 'PlaceholderAPI' installieren");
        } else {
            new PlaceholderProvider().register();
            logger.log(Level.INFO, "Hooked in 'PlaceholderAPI'");
        }
    }

    public void startMoneyGiveTask() {
        int delay = 20 * 60 * 10;
        int period = 20 * 60 * 10;

        new MoneyGiveTask().runTaskTimer(this, delay, period);
    }

    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();
    }

    private void initMysql() {
        MySQLConnection con = new MySQLConnection( this.getConfig().getString("mysql.host"), this.getConfig().getInt("mysql.port"), this.getConfig().getString("mysql.database"), this.getConfig().getString("mysql.username"), this.getConfig().getString("mysql.password"));
        con.connect();
        dataBase = new DataBase(this);
    }


    private void createTables() {
        try (PreparedStatement statement = MySQLConnection.getConnection().prepareStatement(
                "CREATE TABLE IF NOT EXISTS player_money (uuid VARCHAR(36) PRIMARY KEY, name VARCHAR(100), money DOUBLE, bank DOUBLE)")) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void registerCommands() {
        getCommand("admin").setExecutor(new admin());
        getCommand("fly").setExecutor(new fly());
        getCommand("heal").setExecutor(new heal());
        getCommand("feed").setExecutor(new feed());
        getCommand("navi").setExecutor(new navigator());
        getCommand("balance").setExecutor(new balance(dataBase));
        getCommand("balancetop").setExecutor(new balancetop(dataBase));
        getCommand("money").setExecutor(new balance(dataBase));
        getCommand("bank").setExecutor(new bank(dataBase));
        getCommand("pay").setExecutor(new pay());
        getCommand("eco").setExecutor(new eco());
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
        getCommand("spawn").setExecutor(new spawn());
        getCommand("setspawn").setExecutor(new setSpawn(this));
        getCommand("repair").setExecutor(new repair());
    }

    public void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new navi(instance), this);
        Bukkit.getPluginManager().registerEvents(new Listener(), this);
        Bukkit.getPluginManager().registerEvents(new AdminListener(), this);
        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
        Bukkit.getPluginManager().registerEvents(new ChestShopListener(), this);
        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        Bukkit.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this::onPluginMessageReceived);
    }

    public void loadConfig() {

        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }
        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            saveResource("config.yml", false);
        }
        this.saveDefaultConfig();
        if (!this.getConfig().contains("mysql.host")) {
            this.getConfig().set("plugin.name", "megaessentials");
            this.getConfig().set("mysql.host", "localhost");
            this.getConfig().set("mysql.port", 3306);
            this.getConfig().set("mysql.database", "essentials");
            this.getConfig().set("mysql.username", "user");
            this.getConfig().set("mysql.password", "password");

            this.getConfig().set("spawn.x", "1");
            this.getConfig().set("spawn.y", "1");
            this.getConfig().set("spawn.z", "1");
            this.getConfig().set("spawn.world", "world");

            this.getConfig().set("plugin.name", "MegaEssentials");
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

    public static Plugin getInstance() {
        return instance;
    }

    public static String getPluginName() {
        return name;
    }

    public static EconomyProvider getEconomyProvider() {
        return economyProvider;
    }

    @Override
    public void onDisable() {
        MySQLConnection.disconnect();
    }
}
