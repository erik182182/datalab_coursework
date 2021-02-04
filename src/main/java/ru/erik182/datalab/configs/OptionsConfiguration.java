package ru.erik182.datalab.configs;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import java.util.Properties;

public class OptionsConfiguration {

    private OptionsConfiguration() {
    }


    public static void optionsInit(Properties properties, Options options) {
        Option usernameOption = new Option(
                properties.getProperty("option.username.short"),
                properties.getProperty("option.username.long"),
                true,
                properties.getProperty("option.username.description")
        );
        usernameOption.setArgs(1); // число аргументов в опции

        Option hostOption = new Option(
                properties.getProperty("option.host.short"),
                properties.getProperty("option.host.long"),
                true,
                properties.getProperty("option.host.description")
        );
        hostOption.setArgs(1); // число аргументов в опции

        Option passwordOption = new Option(
                properties.getProperty("option.password.short"),
                properties.getProperty("option.password.long"),
                true,
                properties.getProperty("option.password.description")
        );
        passwordOption.setArgs(1); // число аргументов в опции

        Option portOption = new Option(
                properties.getProperty("option.port.short"),
                properties.getProperty("option.port.long"),
                true,
                properties.getProperty("option.port.description")
        );
        portOption.setArgs(1); // число аргументов в опции

        Option dbnameOption = new Option(
                properties.getProperty("option.dbname.short"),
                properties.getProperty("option.dbname.long"),
                true,
                properties.getProperty("option.dbname.description")
        );
        dbnameOption.setArgs(1); // число аргументов в опции

        Option tablenameOption = new Option(
                properties.getProperty("option.tablename.short"),
                properties.getProperty("option.tablename.long"),
                true,
                properties.getProperty("option.tablename.description")
        );
        tablenameOption.setArgs(1); // число аргументов в опции

        Option columnnameOption = new Option(
                properties.getProperty("option.columnname.short"),
                properties.getProperty("option.columnname.long"),
                true,
                properties.getProperty("option.columnname.description")
        );
        columnnameOption.setArgs(1); // число аргументов в опции

        Option modeOption = new Option(
                properties.getProperty("option.mode.short"),
                properties.getProperty("option.mode.long"),
                true,
                properties.getProperty("option.mode.description")
        );
        modeOption.setArgs(1); // число аргументов в опции

        Option dbCopyOption = new Option(
                properties.getProperty("option.exportdb.short"),
                properties.getProperty("option.exportdb.long"),
                false,
                properties.getProperty("option.exportdb.description")
        );
        dbCopyOption.setArgs(0); // число аргументов в опции

        Option helpOption = new Option(
                properties.getProperty("option.help.short"),
                properties.getProperty("option.help.long"),
                false,
                properties.getProperty("option.help.description")
        );
        helpOption.setArgs(0); // число аргументов в опции

        Option showDatabasesOption = new Option(
                properties.getProperty("option.showdb.short"),
                properties.getProperty("option.showdb.long"),
                false,
                properties.getProperty("option.showdb.description")
        );
        showDatabasesOption.setArgs(0); // число аргументов в опции

        Option showUsersOption = new Option(
                properties.getProperty("option.showusers.short"),
                properties.getProperty("option.showusers.long"),
                false,
                properties.getProperty("option.showusers.description")
        );
        showUsersOption.setArgs(0); // число аргументов в опции

        Option showTablesOption = new Option(
                properties.getProperty("option.showtables.short"),
                properties.getProperty("option.showtables.long"),
                false,
                properties.getProperty("option.showtables.description")
        );
        showTablesOption.setArgs(0); // число аргументов в опции

        Option showColumnsOption = new Option(
                properties.getProperty("option.showcolumns.short"),
                properties.getProperty("option.showcolumns.long"),
                false,
                properties.getProperty("option.showcolumns.description")
        );
        showColumnsOption.setArgs(0); // число аргументов в опции

        Option obfOption = new Option(
                properties.getProperty("option.obf.short"),
                properties.getProperty("option.obf.long"),
                false,
                properties.getProperty("option.obf.description")
        );
        obfOption.setArgs(0); // число аргументов в опции

        options.addOption(usernameOption);
        options.addOption(hostOption);
        options.addOption(passwordOption);
        options.addOption(portOption);
        options.addOption(dbnameOption);
        options.addOption(tablenameOption);
        options.addOption(columnnameOption);
        options.addOption(modeOption);
        options.addOption(dbCopyOption);
        options.addOption(helpOption);
        options.addOption(showDatabasesOption);
        options.addOption(showUsersOption);
        options.addOption(showTablesOption);
        options.addOption(showColumnsOption);
        options.addOption(obfOption);
    }

}
