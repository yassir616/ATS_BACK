// package com.soa.vie.takaful.DBConfig;

// import javax.sql.DataSource;

// import org.springframework.beans.factory.annotation.Qualifier;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.jdbc.core.JdbcTemplate;
// import org.springframework.jdbc.datasource.DriverManagerDataSource;

// @Configuration
// public class DatabaseConfig2 {

// @Value("${second.database.url}")
// private String newDatabaseUrl;

// @Value("${second.database.username}")
// private String newDatabaseUsername;

// @Value("${second.database.password}")
// private String newDatabasePassword;

// @Bean(name = "newDataSource")
// public DataSource newDataSource() {
// DriverManagerDataSource dataSource = new DriverManagerDataSource();
// dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
// dataSource.setUrl(newDatabaseUrl);
// dataSource.setUsername(newDatabaseUsername);
// dataSource.setPassword(newDatabasePassword);
// return dataSource;
// }

// @Bean(name = "newJdbcTemplate")
// public JdbcTemplate newJdbcTemplate(@Qualifier("newDataSource") DataSource
// newDataSource) {
// return new JdbcTemplate(newDataSource);
// }

// }