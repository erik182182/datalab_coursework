package ru.erik182.datalab.repositories;

import ru.erik182.datalab.models.ColumnInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBMetaInfoRepository {

    private static final String SQL_GET_DB_NAMES = "SELECT datname FROM pg_database;";
    private static final String SQL_GET_USERNAMES = "select usename from pg_shadow;";
    private static final String SQL_GET_TABLENAMES = "SELECT table_name FROM information_schema.tables " +
            "WHERE table_schema NOT IN ('information_schema','pg_catalog');";
    private static final String SQL_GET_COLUMNS = "SELECT * FROM information_schema.columns " +
            "WHERE table_schema = 'public' AND table_name = ?;";
    private final Connection connection;

    public DBMetaInfoRepository(Connection connection) {
        this.connection = connection;
    }

    public List<String> getDbNames() {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_GET_DB_NAMES);
            ResultSet resultSet = statement.executeQuery();
            List<String> names = new ArrayList<>();
            while (resultSet.next()) {
                String dbName = resultSet.getString("datname");
                names.add(dbName);
            }
            return names;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public List<String> getUserNames() {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_GET_USERNAMES);
            ResultSet resultSet = statement.executeQuery();
            List<String> names = new ArrayList<>();
            while (resultSet.next()) {
                String name = resultSet.getString("usename");
                names.add(name);
            }
            return names;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public List<String> getTableNames(){
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_GET_TABLENAMES);
            ResultSet resultSet = statement.executeQuery();
            List<String> names = new ArrayList<>();
            while (resultSet.next()) {
                String name = resultSet.getString("table_name");
                names.add(name);
            }
            return names;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public List<ColumnInfo> getColumnInfo(String tablename){
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_GET_COLUMNS);
            statement.setString(1, tablename);
            ResultSet resultSet = statement.executeQuery();
            List<ColumnInfo> columnInfos = new ArrayList<>();
            while (resultSet.next()) {
                String dataType = resultSet.getString("data_type");
                String charMaxLength = resultSet.getString("character_maximum_length");
                if(charMaxLength != null && !charMaxLength.isEmpty()){
                    dataType = dataType + "(" + charMaxLength + ")";
                }
                ColumnInfo columnInfo = ColumnInfo.builder()
                        .name(resultSet.getString("column_name"))
                        .dataType(dataType)
                        .build();
                columnInfos.add(columnInfo);
            }
            return columnInfos;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
