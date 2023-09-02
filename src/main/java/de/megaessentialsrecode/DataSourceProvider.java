package de.megaessentialsrecode;

import de.megaessentialsrecode.utils.ConnectionPoolFactory;
import org.bukkit.plugin.Plugin;

import javax.sql.DataSource;
import java.sql.SQLException;

public class DataSourceProvider {
    private ConnectionPoolFactory factory;

    public DataSourceProvider(ConnectionPoolFactory factory) {
        this.factory = factory;
    }

    /**
     * Get a data source for the plugin.
     * <p>
     * A data source will be valid till the server is shut down. A reload will not invalidate the data source.
     * <p>
     * A plugin can not request multiple data sources.
     *
     * @param plugin plugin which requests the data source
     * @return a new data source for the plugin or a already created data source.
     * @throws SQLException if the data source could not be created for some reason.
     */
    public DataSource getPluginDataSource(Plugin plugin) throws SQLException {
        return factory.getPluginDataSource(plugin);
    }
}
