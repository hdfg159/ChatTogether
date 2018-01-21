package hdfg159.chattogether.config;

import hdfg159.chattogether.constant.JdbcConsts;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.config
 * Created by hdfg159 on 2017/6/29 23:12.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "hdfg159.chattogether.data", repositoryImplementationPostfix = "Helper")
public class JpaConfig {
	
	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setDatabase(Database.MYSQL);
		adapter.setShowSql(true);
		adapter.setGenerateDdl(true);
		//		adapter.setDatabase(Database.H2);
		//		adapter.setDatabasePlatform("org.hibernate.dialect.H2Dialect");
		adapter.setDatabasePlatform("org.hibernate.dialect.MySQL5InnoDBDialect");
		return adapter;
	}
	
	@Bean
	LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource);
		entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
		entityManagerFactoryBean.setPackagesToScan("hdfg159.chattogether.domain");
		return entityManagerFactoryBean;
	}
	
	@Bean
	public JpaTransactionManager transactionManager() {
		return new JpaTransactionManager();
	}

//	@Bean
//	public DataSource embeddedDataSource() {
//		return new EmbeddedDatabaseBuilder()
////				.addScript("classpath:/test-data.sql")
//				.setType(EmbeddedDatabaseType.H2)
//				.build();
//	}
	
	@Bean
	public DataSource mySQLDataSource() {
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		driverManagerDataSource.setDriverClassName(JdbcConsts.DRIVERCLASSNAME_MYSQL);
		driverManagerDataSource.setUrl(JdbcConsts.JDBC_URL);
		driverManagerDataSource.setUsername(JdbcConsts.DATASOURCE_USERNAME);
		driverManagerDataSource.setPassword(JdbcConsts.DATASOURE_PASSWORD);
		return driverManagerDataSource;
	}
}
