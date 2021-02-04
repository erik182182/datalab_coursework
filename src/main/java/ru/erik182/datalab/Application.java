package ru.erik182.datalab;

import org.apache.commons.cli.*;
import org.apache.log4j.Logger;
import ru.erik182.datalab.configs.OptionsConfiguration;
import ru.erik182.datalab.enums.ObfuscationModeEnum;
import ru.erik182.datalab.exceptions.ExportDBException;
import ru.erik182.datalab.exceptions.InitException;
import ru.erik182.datalab.models.ColumnInfo;
import ru.erik182.datalab.repositories.DBMetaInfoRepository;
import ru.erik182.datalab.repositories.ObfuscationRepository;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Application {

    private static final String PROPERTIES_URL = "src/main/resources/application.properties";
    private static final Logger log = Logger.getLogger(Application.class);
    private static String TMP_URL;
    private static String BAT_URL;
    private static String BAT_EX_NAME;
    private static String CMD_START;
    private static Properties properties;
    private static final Options options = new Options();

    public static void main(String[] args) {
        try {
            init();
            perform(args);
        } catch (InitException e) {
            log.error("An error occurred while initializing the program.", e);
            System.exit(1);
        } catch (ParseException e) {
            log.error("Launch parameters error. Please use --help.");
            System.exit(1);
        } catch (ExportDBException e) {
            log.error(e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            log.error("An unexpected error has occurred.", e);
            System.exit(1);
        }
    }

    public static void init() throws InitException {
        // property file init
        FileInputStream fis;
        properties = new Properties();

        try {
            fis = new FileInputStream(PROPERTIES_URL);
            properties.load(fis);
        } catch (IOException e) {
            throw new InitException("A property initialization error.");
        }

        BAT_URL = properties.getProperty("path.bat");
        TMP_URL = properties.getProperty("path.tmp");
        BAT_EX_NAME = properties.getProperty("file.bat.ex");
        CMD_START = properties.getProperty("app.cmd.start");

        //cmd options of application
        OptionsConfiguration.optionsInit(properties, options);
    }

    public static void perform(String[] args) throws ParseException, ExportDBException, SQLException, ClassNotFoundException {
        CommandLineParser cmdLineParser = new DefaultParser();// создаем парсер
        CommandLine commandLine = cmdLineParser.parse(options, args);// парсим командную строку
        if (commandLine.hasOption(properties.getProperty("option.help.short"))) {
            help();
            System.exit(0);
        }
        if (commandLine.hasOption(properties.getProperty("option.exportdb.short"))) {
            exportDB();
            System.exit(0);
        }
        if (commandLine.hasOption(properties.getProperty("option.showdb.short"))) {
            if (
                    commandLine.hasOption(properties.getProperty("option.host.short")) &&
                            commandLine.hasOption(properties.getProperty("option.username.short")) &&
                            commandLine.hasOption(properties.getProperty("option.password.short"))
            ) {
                String host = commandLine.getOptionValue(properties.getProperty("option.host.short"));
                String username = commandLine.getOptionValue(properties.getProperty("option.username.short"));
                String password = commandLine.getOptionValue(properties.getProperty("option.password.short"));
                String port;
                if (commandLine.hasOption(properties.getProperty("option.port.short"))) {
                    port = commandLine.getOptionValue(properties.getProperty("option.port.short"));
                } else {
                    port = properties.getProperty("port.default");
                }
                System.out.println("DB Names:");
                showDB("jdbc:postgresql://" + host + ":" + port + "/", username, password);
                System.exit(0);
            } else {
                throw new ParseException("Arguments error.");
            }
        }
        if (commandLine.hasOption(properties.getProperty("option.showusers.short"))) {
            if (
                    commandLine.hasOption(properties.getProperty("option.host.short")) &&
                            commandLine.hasOption(properties.getProperty("option.username.short")) &&
                            commandLine.hasOption(properties.getProperty("option.password.short"))
            ) {
                String host = commandLine.getOptionValue(properties.getProperty("option.host.short"));
                String username = commandLine.getOptionValue(properties.getProperty("option.username.short"));
                String password = commandLine.getOptionValue(properties.getProperty("option.password.short"));
                String port;
                if (commandLine.hasOption(properties.getProperty("option.port.short"))) {
                    port = commandLine.getOptionValue(properties.getProperty("option.port.short"));
                } else {
                    port = properties.getProperty("port.default");
                }
                System.out.println("User names:");
                showUsers("jdbc:postgresql://" + host + ":" + port + "/", username, password);
                System.exit(0);
            } else {
                throw new ParseException("Arguments error.");
            }
        }
        if (commandLine.hasOption(properties.getProperty("option.showtables.short"))) {
            if (
                    commandLine.hasOption(properties.getProperty("option.host.short")) &&
                            commandLine.hasOption(properties.getProperty("option.username.short")) &&
                            commandLine.hasOption(properties.getProperty("option.password.short")) &&
                            commandLine.hasOption(properties.getProperty("option.dbname.short"))
            ) {
                String host = commandLine.getOptionValue(properties.getProperty("option.host.short"));
                String username = commandLine.getOptionValue(properties.getProperty("option.username.short"));
                String password = commandLine.getOptionValue(properties.getProperty("option.password.short"));
                String port;
                if (commandLine.hasOption(properties.getProperty("option.port.short"))) {
                    port = commandLine.getOptionValue(properties.getProperty("option.port.short"));
                } else {
                    port = properties.getProperty("port.default");
                }
                String dbname = commandLine.getOptionValue(properties.getProperty("option.dbname.short"));
                System.out.println("Table names:");
                showTables("jdbc:postgresql://" + host + ":" + port + "/" + dbname, username, password);
                System.exit(0);
            } else {
                throw new ParseException("Arguments error.");
            }
        }
        if (commandLine.hasOption(properties.getProperty("option.showcolumns.short"))) {
            if (
                    commandLine.hasOption(properties.getProperty("option.host.short")) &&
                            commandLine.hasOption(properties.getProperty("option.username.short")) &&
                            commandLine.hasOption(properties.getProperty("option.password.short")) &&
                            commandLine.hasOption(properties.getProperty("option.dbname.short")) &&
                            commandLine.hasOption(properties.getProperty("option.tablename.short"))
            ) {
                String host = commandLine.getOptionValue(properties.getProperty("option.host.short"));
                String username = commandLine.getOptionValue(properties.getProperty("option.username.short"));
                String password = commandLine.getOptionValue(properties.getProperty("option.password.short"));
                String port;
                if (commandLine.hasOption(properties.getProperty("option.port.short"))) {
                    port = commandLine.getOptionValue(properties.getProperty("option.port.short"));
                } else {
                    port = properties.getProperty("port.default");
                }
                String dbname = commandLine.getOptionValue(properties.getProperty("option.dbname.short"));
                String tablename = commandLine.getOptionValue(properties.getProperty("option.tablename.short"));
                System.out.println("Column names:");
                showColumns("jdbc:postgresql://" + host + ":" + port + "/" + dbname, tablename, username, password);
                System.exit(0);
            } else {
                throw new ParseException("Arguments error.");
            }
        }
        if (commandLine.hasOption(properties.getProperty("option.obf.short"))) {
            if (
                    commandLine.hasOption(properties.getProperty("option.host.short")) &&
                            commandLine.hasOption(properties.getProperty("option.username.short")) &&
                            commandLine.hasOption(properties.getProperty("option.password.short")) &&
                            commandLine.hasOption(properties.getProperty("option.dbname.short")) &&
                            commandLine.hasOption(properties.getProperty("option.tablename.short")) &&
                            commandLine.hasOption(properties.getProperty("option.columnname.short")) &&
                            commandLine.hasOption(properties.getProperty("option.mode.short"))
            ) {
                String host = commandLine.getOptionValue(properties.getProperty("option.host.short"));
                String username = commandLine.getOptionValue(properties.getProperty("option.username.short"));
                String password = commandLine.getOptionValue(properties.getProperty("option.password.short"));
                String port;
                if (commandLine.hasOption(properties.getProperty("option.port.short"))) {
                    port = commandLine.getOptionValue(properties.getProperty("option.port.short"));
                } else {
                    port = properties.getProperty("port.default");
                }
                String dbname = commandLine.getOptionValue(properties.getProperty("option.dbname.short"));
                String tablename = commandLine.getOptionValue(properties.getProperty("option.tablename.short"));
                String columnnames = commandLine.getOptionValue(properties.getProperty("option.columnname.short"));
                String mode = commandLine.getOptionValue(properties.getProperty("option.mode.short"));
                obfuscate("jdbc:postgresql://" + host + ":" + port + "/" + dbname, username, password, tablename, columnnames, mode);
                System.exit(0);
            } else {
                throw new ParseException("Arguments error.");
            }
        }
    }

    private static void help() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("obfdata", options);
    }

    private static void exportDB() throws ExportDBException {
        try {
            Process proc = Runtime.getRuntime().exec(CMD_START + " " + BAT_URL + BAT_EX_NAME);
            proc.waitFor();
            proc.destroy();
        } catch (Exception e) {
            throw new ExportDBException("An error occurred while exporting the database.");
        }
    }

    private static void showDB(String host, String username, String password) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection(host, username, password);
        DBMetaInfoRepository repository = new DBMetaInfoRepository(connection);
        for (String name : repository.getDbNames()) {
            System.out.println(name);
        }
    }

    private static void showUsers(String host, String username, String password) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection(host, username, password);
        DBMetaInfoRepository repository = new DBMetaInfoRepository(connection);
        for (String name : repository.getUserNames()) {
            System.out.println(name);
        }
    }

    private static void showTables(String host, String username, String password) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection(host, username, password);
        DBMetaInfoRepository repository = new DBMetaInfoRepository(connection);
        for (String name : repository.getTableNames()) {
            System.out.println(name);
        }
    }

    private static void showColumns(String host, String tablename, String username, String password) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection(host, username, password);
        DBMetaInfoRepository repository = new DBMetaInfoRepository(connection);
        for (ColumnInfo columnInfo : repository.getColumnInfo(tablename)) {
            System.out.println(columnInfo.getName() + " [type=" + columnInfo.getDataType() + "]");
        }
    }

    private static void obfuscate(String host, String username, String password, String tablename, String columnnames, String mode) throws ClassNotFoundException, SQLException, ParseException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection(host, username, password);
        ObfuscationRepository repository = new ObfuscationRepository(connection);
        for(String columnname: columnnames.split(properties.getProperty("obf.columnname.separator"))){
            try {
                switch (ObfuscationModeEnum.valueOf(mode.toUpperCase())) {
                    case MD5: {
                        repository.obfuscateColumnMd5(tablename, columnname);
                    }
                    break;
                    case NULL: {
                        repository.obfuscateColumnNull(tablename, columnname);
                    }
                    break;
                    case DISP: {
                        repository.obfuscateColumnDispersion(tablename, columnname, Double.parseDouble(properties.getProperty("obf.disp.percent")));
                    }
                    break;
                    default:
                        throw new ParseException("This obfuscation mode does not exist.");
                }
            } catch (SQLException e) {
                throw new SQLException("An error occurred while obfuscating the column. The column may have an incompatible data type, or the obfuscation mode was chosen incorrectly.", e);
            } catch (IllegalArgumentException e) {
                throw new ParseException("This obfuscation mode does not exist.");
            }
        }
    }
}
