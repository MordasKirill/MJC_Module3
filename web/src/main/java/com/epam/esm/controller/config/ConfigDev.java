package com.epam.esm.controller.config;

import com.epam.esm.dao.connection.DBParameter;
import com.epam.esm.dao.connection.DBResourceManager;
import org.h2.jdbcx.JdbcConnectionPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ComponentScan("com.epam.esm")
@Configuration
@EnableTransactionManagement
@Profile("dev")
public class ConfigDev {

    @Autowired
    private DBResourceManager dbResourceManager;

    @Bean
    public JdbcConnectionPool getDataSource() {
        dbResourceManager.loadProperties("test.properties");
        return JdbcConnectionPool.create(dbResourceManager.properties.getProperty(DBParameter.DB_URL), dbResourceManager.properties.getProperty(DBParameter.DB_USER),
                dbResourceManager.properties.getProperty(DBParameter.DB_PASSWORD));
    }
}
