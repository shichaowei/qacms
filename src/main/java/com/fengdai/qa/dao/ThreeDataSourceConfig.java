package com.fengdai.qa.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class ThreeDataSourceConfig {

	@Bean(name="oldFDJdbcTemplate")
    public JdbcTemplate testJdbcTemplate(@Qualifier("threeDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}