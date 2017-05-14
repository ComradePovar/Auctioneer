package edu.core.java.auction;

import com.zaxxer.hikari.HikariConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by Max on 14.04.2017.
 */
public class PropertiesProvider {
    private static Logger logger = LoggerFactory.getLogger(PropertiesProvider.class);

    private String applicationPropertiesName = "application.properties";
    private String databaseConnectionPropertiesName = "databaseConnection.properties";

    private Properties applicationProperties;
    private Properties databaseConnectionProperties;
    private static class Holder {
        private static final PropertiesProvider instance = new PropertiesProvider();
    }

    public static PropertiesProvider getInstance(){
        return Holder.instance;
    }

    private PropertiesProvider(){

    }

    public Properties getApplicationProperties(){
        if (applicationProperties == null) {
            applicationProperties = new Properties();
            try {
                InputStream in = this.getClass().getClassLoader().getResourceAsStream(applicationPropertiesName);
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                try {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        int separatorIndex = line.indexOf('=');
                        String key = line.substring(0, separatorIndex);
                        String value = line.substring(separatorIndex + 1, line.length());
                        applicationProperties.setProperty(key, value);
                    }
                } finally {
                    reader.close();
                    in.close();
                }
            } catch (IOException ex) {
                logger.error(ex.getMessage());
            }
        }

        return applicationProperties;
    }
    public Properties getDatabaseConnectionProperties(){
        if (databaseConnectionProperties == null) {
            databaseConnectionProperties = new Properties();
            try {
                InputStream in = this.getClass().getClassLoader().getResourceAsStream(databaseConnectionPropertiesName);
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                try {
                    databaseConnectionProperties.load(in);
                } finally {
                    reader.close();
                    in.close();
                }
            } catch (IOException ex) {
                logger.error(ex.getMessage());
            }
        }

        return databaseConnectionProperties;
    }
}
