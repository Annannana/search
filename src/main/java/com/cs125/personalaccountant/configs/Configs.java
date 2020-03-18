package com.cs125.personalaccountant.configs;

import com.cs125.personalaccountant.logger.ServiceLogger;
import com.cs125.personalaccountant.models.ConfigsModel;
import com.cs125.personalaccountant.exception.NotFoundValueException;

import java.lang.*;

public class Configs {
    // Service configs
    private String scheme;
    private String hostName;
    private int    port;
    private String path;

    // Logger configs
    private String outputDir;
    private String outputFile;

    //Database configs
    private String dbUsername;
    private String dbPassword;
    private String dbHostname;
    private int dbPort;
    private String dbDriver;
    private String dbName;
    private String dbSettings;

    public Configs(ConfigsModel cm) throws NotFoundValueException {
        if (cm == null) {
            throw new NotFoundValueException("Unable to create Configs from ConfigsModel.");
        } else {
            scheme = cm.getServiceConfig().get("scheme");
            if (scheme == null) {
                throw new NotFoundValueException("Scheme not found in configuration file.");
            }

            hostName = cm.getServiceConfig().get("hostName");
            if (hostName == null) {
                throw new NotFoundValueException("Hostname not found in configuration file. Using default.");
            }

            port = Integer.parseInt(cm.getServiceConfig().get("port"));
            if (port == 0) {
                throw new NotFoundValueException("Port not found in configuration file.");
            } else if (port < 1024 || port > 65536) {
                throw new NotFoundValueException("Port is not within valid range.");
            }

            path = cm.getServiceConfig().get("path");
            if (path == null) {
                throw new NotFoundValueException("Path not found in configuration file.");
            }

            // Set logger configs
            outputDir = cm.getLoggerConfig().get("outputDir");
            if (outputDir == null) {
                throw new NoClassDefFoundError("Logging output directory not found in configuration file.");
            }

            outputFile = cm.getLoggerConfig().get("outputFile");
            if (outputFile == null) {
                throw new NoClassDefFoundError("Logging output file not found in configuration file.");
            }

            dbUsername = cm.getDatabaseConfig().get("dbUsername");
            if (dbUsername == null) {
                throw new NoClassDefFoundError("dbUsername not found in configuration file.");
            }

            dbHostname = cm.getDatabaseConfig().get("dbHostname");
            if (dbHostname == null) {
                throw new NoClassDefFoundError("dbHostname not found in configuration file.");
            }


            if (cm.getDatabaseConfig().get("dbPort") == null) {
                throw new NoClassDefFoundError("dbPort not found in configuration file.");
            } else {
                dbPort = Integer.parseInt(cm.getDatabaseConfig().get("dbPort"));
            }

            dbDriver = cm.getDatabaseConfig().get("dbDriver");
            if (dbDriver == null) {
                throw new NotFoundValueException("dbDriver not found in configuration file.");
            }

            dbName = cm.getDatabaseConfig().get("dbName");
            if (dbName == null) {
                throw new NotFoundValueException("dbName not found in configuration file.");
            }

            dbPassword = cm.getDatabaseConfig().get("dbPassword");
            if (dbPassword == null) {
                throw new NotFoundValueException("dbPassword not found in configuration file.");
            }

            dbSettings = cm.getDatabaseConfig().get("dbSettings");
            if (dbSettings == null) {
                dbSettings = "";
            }
        }
    }

    public void currentConfigs() {
        ServiceLogger.LOGGER.config("Scheme: " + scheme);
        ServiceLogger.LOGGER.config("Hostname: " + hostName);//IP addr or domain name to identify a computer
        ServiceLogger.LOGGER.config("Port: " + port);
        ServiceLogger.LOGGER.config("Path: " + path);
        ServiceLogger.LOGGER.config("Logger output directory: " + outputDir);
        ServiceLogger.LOGGER.config("dbUsername: " + dbUsername);
        ServiceLogger.LOGGER.config("dbPassword: ****" );
        ServiceLogger.LOGGER.config("dbPort: " + dbPort);
        ServiceLogger.LOGGER.config("dbDriver: " + dbDriver);
        ServiceLogger.LOGGER.config("dbName: " + dbName);
        ServiceLogger.LOGGER.config("dbSettings: " + dbSettings);
    }
    public String getScheme() {
        return scheme;
    }

    public String getHostName() {
        return hostName;
    }

    public int getPort() {
        return port;
    }

    public String getPath() {
        return path;
    }

    public String getOutputDir() {
        return outputDir;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public String getDbUsername() {
        return dbUsername;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public String getDbHostname() {
        return dbHostname;
    }

    public int getDbPort() {
        return dbPort;
    }

    public String getDbDriver() {
        return dbDriver;
    }

    public String getDbName() {
        return dbName;
    }

    public String getDbSettings() {
        return dbSettings;
    }
}


