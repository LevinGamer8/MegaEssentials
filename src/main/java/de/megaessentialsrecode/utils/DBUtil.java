package de.megaessentialsrecode.utils;

import de.megaessentialsrecode.MegaEssentials;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

public final class DBUtil {
    public static String prettySQLException(SQLException ex) {
        return "SQLException: " + ex.getMessage() + "\n"
                + "SQLState: " + ex.getSQLState() + "\n"
                + "VendorError: " + ex.getErrorCode();
    }

    public static String rowToString(ResultSet rs) throws SQLException {
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
            list.add(rs.getString(i));
        }

        return String.join(", ", list);
    }


}
