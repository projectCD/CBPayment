package com.CBpayments.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan({ "com.CBpayments.serviceImp", "com.CBpayments.dao" })
@PropertySource(value = { "classpath:application.properties" })
public class ApRootConfig {

	@Autowired
	private Environment env;


	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getRequiredProperty("jdbc.driverClassName"));
		dataSource.setUrl(env.getRequiredProperty("jdbc.url"));
		dataSource.setUsername(env.getRequiredProperty("jdbc.username"));
		dataSource.setPassword(env.getRequiredProperty("jdbc.password"));
		return dataSource;
	}

//	 @Bean
//	 public JdbcTemplate jdbcTemplate(DataSource dataSource) {
//	 JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
//	 jdbcTemplate.setResultsMapCaseInsensitive(true);
//	 return jdbcTemplate;
//	 }

	
	 @Bean
	    public LocalSessionFactoryBean sessionFactory() {
	        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
	        sessionFactory.setDataSource(dataSource());
	        sessionFactory.setPackagesToScan("com.CBpayments.model");
	        sessionFactory.setHibernateProperties(hibernateProperties());
	        return sessionFactory;
	    }
	
	
	 private Properties hibernateProperties() {
	        Properties properties = new Properties();
	        properties.put(AvailableSettings.DIALECT, "org.hibernate.dialect.MySQLDialect");
	        properties.put(AvailableSettings.SHOW_SQL, Boolean.FALSE);
	        properties.put(AvailableSettings.STATEMENT_BATCH_SIZE, 20);
	        properties.put(AvailableSettings.CURRENT_SESSION_CONTEXT_CLASS, "org.springframework.orm.hibernate5.SpringSessionContext");
	        properties.put(AvailableSettings.C3P0_MIN_SIZE, 5);
	        properties.put(AvailableSettings.C3P0_MAX_SIZE, 20);
	        properties.put(AvailableSettings.C3P0_TIMEOUT, 10 * 60 * 1000);
	        return properties;
	    }

	  @Bean
	    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
	        HibernateTransactionManager manager = new HibernateTransactionManager();
	        manager.setSessionFactory(sessionFactory);
	        return manager;
	    }
}
