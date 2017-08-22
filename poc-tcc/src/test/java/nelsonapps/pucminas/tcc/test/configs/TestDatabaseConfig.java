package nelsonapps.pucminas.tcc.test.configs;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@PropertySource({"classpath:testDatabaseConfig.properties"})
@EnableJpaRepositories(
		basePackages={"nelsonapps.pucminas.tcc.persistence.repository"},
		entityManagerFactoryRef="testEntityManagerFactory",
        transactionManagerRef="testTransactionManager")

public class TestDatabaseConfig {

	@Autowired
	private Environment env;
	
	@Bean
	public LocalContainerEntityManagerFactoryBean testEntityManagerFactory(){
		LocalContainerEntityManagerFactoryBean entityManagerFac = 
				new LocalContainerEntityManagerFactoryBean();
		entityManagerFac.setDataSource(testDataSource());
		entityManagerFac.setPackagesToScan(new String[]{"nelsonapps.pucminas.tcc.persistence.entities"});
		entityManagerFac.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		entityManagerFac.setJpaProperties(testJpaProperties());
		return entityManagerFac;
		
	}

	Properties testJpaProperties() {
		Properties jpaProperties = new Properties();
		jpaProperties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
		jpaProperties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
		jpaProperties.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		return jpaProperties;
	}

	@Bean
	public DataSource testDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
		dataSource.setUrl(env.getProperty("jdbc.url"));
		dataSource.setUsername(env.getProperty("jdbc.user"));
		dataSource.setPassword(env.getProperty("jdbc.pass"));
		return dataSource;
	}
	
	@Bean
	public Jackson2RepositoryPopulatorFactoryBean testRepositoryPopulator(){
		Resource sourceData = new ClassPathResource("testData.json");
		Jackson2RepositoryPopulatorFactoryBean jacksonBean = new Jackson2RepositoryPopulatorFactoryBean();
		jacksonBean.setResources(new Resource[]{sourceData});
		return jacksonBean;
		
		
	}
	@Bean
	public PlatformTransactionManager testTransactionManager(){
		return new JpaTransactionManager(testEntityManagerFactory().getObject());
	}
}
