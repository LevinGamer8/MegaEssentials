package de.megaessentialsrecode.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import de.megaessentialsrecode.MegaEssentials;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.Plugin;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;

public class ConnectionPoolFactory {

    private final Configuration config;
    private final Map<Class<? extends Plugin>, HikariDataSource> dataPools = new HashMap<>();

    public ConnectionPoolFactory(Configuration config) {
        this.config = config;
    }

    public DataSource getPluginDataSource(Plugin plugin) throws SQLException {
        HikariDataSource dataSource = dataPools.get(plugin.getClass());

        if (dataSource == null) {
            String port = String.valueOf(config.getInt("mysql.port"));

            Properties props = new Properties();
            props.setProperty("dataSourceClassName", "org.mariadb.jdbc.MariaDbDataSource");
            props.setProperty("dataSource.serverName", config.getString("mysql.host"));
            props.setProperty("dataSource.portNumber", port);
            props.setProperty("dataSource.user", config.getString("mysql.username"));
            props.setProperty("dataSource.password", config.getString("mysql.password"));
            props.setProperty("dataSource.databaseName", config.getString("mysql.database"));

            HikariConfig hikariConfig = new HikariConfig(props);
            hikariConfig.setMaximumPoolSize(10);

            dataSource = new HikariDataSource(hikariConfig);
            dataPools.put(plugin.getClass(), dataSource);
        }

        try (Connection conn = dataSource.getConnection()) {
            if (!conn.isValid(5 * 1000)) {
                throw new SQLException("Connection is not valid.");
            }
        } catch (SQLException e) {
            MegaEssentials.logger().log(Level.WARNING, "Invalid data for data source. Could not connect.%n%s", e);
            dataPools.remove(plugin.getClass());
            throw e;
        }

        MegaEssentials.logger().info("Verbindung für MegaEssentials erstellt.");

        return dataSource;
    }

    public void shutdown() {
        for (HikariDataSource value : dataPools.values()) {
            value.close();
        }
        MegaEssentials.logger().info("Verbindung erfolgreich geschlossen");
    }
}
