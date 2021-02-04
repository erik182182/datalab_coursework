package ru.erik182.datalab.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ObfuscationRepository {
    
    private static final String SQL_OBFUSCATE_COLUMN_MD5 = "UPDATE %s " +
            "set %s = left(md5(%s), (SELECT character_maximum_length " +
            "                            FROM information_schema.columns " +
            "                            WHERE table_name = ? " +
            "                              AND column_name = ? ) " +
            "    );";

    private static final String SQL_OBFUSCATE_COLUMN_NULL = "UPDATE %s set %s = null;";

    private static final String SQL_OBFUSCATE_COLUMN_DISP = "UPDATE %s SET %s = %s + (random() * 2 - 1)::int * %s * (random() * %s);";

    private final Connection connection;

    public ObfuscationRepository(Connection connection) {
        this.connection = connection;
    }

    public void obfuscateColumnMd5(String tablename, String columnname) throws SQLException {
        String query = String.format(SQL_OBFUSCATE_COLUMN_MD5, tablename, columnname, columnname);
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, tablename);
        statement.setString(2, columnname);
        statement.executeUpdate();
    }

    public void obfuscateColumnNull(String tablename, String columnname) throws SQLException {
        String query = String.format(SQL_OBFUSCATE_COLUMN_NULL, tablename, columnname);
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
    }

    public void obfuscateColumnDispersion(String tablename, String columnname, double dispProcent) throws SQLException {
        String query = String.format(SQL_OBFUSCATE_COLUMN_DISP, tablename, columnname, columnname, columnname, dispProcent);
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
    }
}
