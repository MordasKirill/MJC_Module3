package com.epam.esm.controller.config;

import com.epam.esm.dao.connection.DBParameter;
import com.epam.esm.dao.connection.DBResourceManager;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.beans.PropertyVetoException;

@ComponentScan("com.epam.esm")
@Configuration
@EnableTransactionManagement
@Profile("prod")
public class ConfigProd {

    @Autowired
    private DBResourceManager dbResourceManager;

    @Bean
    public ComboPooledDataSource getDataSource() throws PropertyVetoException {
        dbResourceManager.loadProperties("db.properties");
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass(dbResourceManager.properties.getProperty(DBParameter.DB_DRIVER));
        comboPooledDataSource.setJdbcUrl(dbResourceManager.properties.getProperty(DBParameter.DB_URL));
        comboPooledDataSource.setUser(dbResourceManager.properties.getProperty(DBParameter.DB_USER));
        comboPooledDataSource.setPassword(dbResourceManager.properties.getProperty(DBParameter.DB_PASSWORD));
        return comboPooledDataSource;
    }
}
