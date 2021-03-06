package com.cs125.personalaccountant;

import javax.ws.rs.core.UriBuilder;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.cs125.personalaccountant.models.ConfigsModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import com.cs125.personalaccountant.exception.NotFoundValueException;
import com.cs125.personalaccountant.configs.Configs;
import com.cs125.personalaccountant.logger.ServiceLogger;

public class BasicService {
    public static BasicService basicService;
    private static Configs configs;
    private static Connection connection;

    public static void main(String[] args){
        basicService = new BasicService();
        basicService.initService(args);
    }

    private void initService(String[] args){
        // Validate arguments
        basicService.validateArguments(args);
        // Exec the arguments
        basicService.execArguments(args);
        // Initialize logging
        initLogging();
        ServiceLogger.LOGGER.config("Starting service...");
        configs.currentConfigs();
        // Connect to database
        connectToDatabase();
        // Initialize HTTP server
        initHTTPServer();
        ServiceLogger.LOGGER.config("Service initialized.");
    }

    private void validateArguments(String[] args) {
        boolean isConfigOptionSet = false;
        for (int i = 0; i < args.length; ++i) {
            switch (args[i]) {
                case "--config":
                case "-c":
                    if (!isConfigOptionSet) {
                        isConfigOptionSet = true;

                        ++i;
                    } else {
                        exitAppFailureArgs("Conflicting configuration file arguments.");
                    }
                    break;
                default:
                    exitAppFailureArgs("Unrecognized argument: " + args[i]);
            }
        }
    }

    private void execArguments(String[] args) {
        if (args.length > 0) {
            for (int i = 0; i < args.length; ++i) {
                switch (args[i]) {
                    case "--config":
                    case "-c":
                        // Config file specified. Load it.
                        getConfigFile(args[i + 1]);
                        ++i;
                        break;
                    default:
                        exitAppFailure("Unrecognized argument: " + args[i]);
                }
            }
        } else {
            System.err.println("No config file specified. Using default values.");
        }
    }

    private void getConfigFile(String configFile) {
        try {
            System.err.println("Config file name: " + configFile);
            configs = new Configs(loadConfigs(configFile));
            System.err.println("Configuration file successfully loaded.");
        } catch (NotFoundValueException e) {
            System.err.println("Config file not found.");
        }
    }

    private ConfigsModel loadConfigs(String file) {
        System.err.println("Loading configuration file...");
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        ConfigsModel configsModel = null;

        try {
            configsModel = mapper.readValue(new File(file), ConfigsModel.class);
        } catch (IOException e) {
            exitAppFailure("Unable to load configuration file.");
        }
        return configsModel;
    }

    private void initLogging() {
        try {
            ServiceLogger.initLogger(configs.getOutputDir(), configs.getOutputFile());
        } catch (IOException e) {
            exitAppFailure("Unable to initialize logging.");
        }
    }

    private void connectToDatabase() {
        ServiceLogger.LOGGER.config("Connecting to database...");

        String url = "jdbc:mysql://" + configs.getDbHostname() + ":" + configs.getDbPort() + "/" + configs.getDbName() + configs.getDbSettings();

        try {
            ServiceLogger.LOGGER.config("Database URL: " + url);
            connection = DriverManager.getConnection(url, configs.getDbUsername(), configs.getDbPassword());
            ServiceLogger.LOGGER.config("Connected to database: " + connection.toString());
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof ClassCastException) {
                ServiceLogger.LOGGER.warning("ClassCastException");
            }
            if (e instanceof SQLException) {
                ServiceLogger.LOGGER.warning("SQLException");
            }
            if (e instanceof NullPointerException) {
                ServiceLogger.LOGGER.warning("NullPointerException");
            }
        }
    }

    private void initHTTPServer() {
        ServiceLogger.LOGGER.config("Initializing HTTP server...");
        String scheme = configs.getScheme();
        String hostName = configs.getHostName();
        int port = configs.getPort();
        String path = configs.getPath();

        try {
            URI uri = UriBuilder.fromUri(scheme + hostName + path).port(port).build();
            ServiceLogger.LOGGER.config("Build uri");
            ResourceConfig rc = new ResourceConfig().packages("com.cs125.personalaccountant.resources");
            ServiceLogger.LOGGER.config("ResourceConfig.");
            rc.register(JacksonFeature.class);
            ServiceLogger.LOGGER.config("Registed.");
            HttpServer server = GrizzlyHttpServerFactory.createHttpServer(uri, rc, false);
            ServiceLogger.LOGGER.config("HTTP server made.");
            server.start();
            ServiceLogger.LOGGER.config("Uri: "+ uri);
            ServiceLogger.LOGGER.config("HTTP server started.");
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private void exitAppFailure(String message) {
        System.err.println("ERROR: " + message);
        System.exit(-1);
    }

    private void exitAppFailureArgs(String message) {
        System.err.println("ERROR: " + message);
        System.err.println("Usage options: ");
        System.err.println("\tSpecify configuration file:");
        System.err.println("\t\t--config [file]");
        System.err.println("\t\t-c");
        System.err.println("\tUse default configuration:");
        System.err.println("\t\t--default");
        System.err.println("\t\t-d");
        System.exit(-1);
    }

    public static Connection getConnection() {
        return connection;
    }

}


