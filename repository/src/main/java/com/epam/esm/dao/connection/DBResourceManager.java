package com.epam.esm.dao.connection;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Component
public class DBResourceManager {
    private static final Logger LOGGER = Logger.getLogger(DBResourceManager.class);
    public final Properties properties = new Properties();

    public void loadProperties(String path) {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
            properties.load(inputStream);
        } catch (IOException e) {
            LOGGER.log(Level.ERROR, "Could not load property file!");
        }
    }
}

