package com.bookstore.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {
    
    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url("jdbc:mysql://10.128.11.102:3306/mcqdb");
        dataSourceBuilder.username("hemanth");
        dataSourceBuilder.password("hemanth");

        return dataSourceBuilder.build();
    }
}
