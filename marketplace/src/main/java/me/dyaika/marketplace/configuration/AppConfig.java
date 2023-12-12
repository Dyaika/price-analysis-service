package me.dyaika.marketplace.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class AppConfig {
    public NamedParameterJdbcTemplate template(DataSource dataSource){
        return new NamedParameterJdbcTemplate(dataSource);
    }
}
