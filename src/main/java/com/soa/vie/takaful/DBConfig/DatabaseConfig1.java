package com.soa.vie.takaful.DBConfig;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DatabaseConfig1 {

    @Value("${spring.datasource.url}")
    private String newDatabaseUrl;

    @Value("${spring.datasource.username}")
    private String newDatabaseUsername;

    @Value("${spring.datasource.password}")
    private String newDatabasePassword;

    @Primary
    @Bean(name = "DataSource")
    public DataSource newDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        dataSource.setUrl(newDatabaseUrl);
        dataSource.setUsername(newDatabaseUsername);
        dataSource.setPassword(newDatabasePassword);
        return dataSource;
    }

}