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
        dataSourceBuilder.url("jdbc:mysql://10.128.10.203:3306/mcqdb");
        dataSourceBuilder.username("root");
        dataSourceBuilder.password("root");

        return dataSourceBuilder.build();
    }
}
