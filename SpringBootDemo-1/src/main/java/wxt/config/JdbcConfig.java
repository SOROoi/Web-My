package wxt.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
public class JdbcConfig {

	@Bean
	@ConfigurationProperties(prefix = "jdbc.datasource")
	public DataSource createDruid() {
		return new DruidDataSource();
	}
}
