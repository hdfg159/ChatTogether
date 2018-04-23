package hdfg159.chattogether.config;

import hdfg159.chattogether.config.condition.DBServerCondition;
import hdfg159.chattogether.config.condition.EmbeddedDBCondition;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

import static java.lang.Boolean.valueOf;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.config
 * Created by hdfg159 on 2017/6/29 23:12.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "hdfg159.chattogether.data", repositoryImplementationPostfix = "Helper")
@PropertySource(value = {"classpath:appconfig.properties"})
public class JpaConfig {
	@Value("${jdbc.password}")
	private String jdbcPassword;
	@Value("${jdbc.username}")
	private String jdbcUsername;
	@Value("${jdbc.url}")
	private String jdbcUrl;
	@Value("${jdbc.driverClassName}")
	private String jdbcDriverclassname;
	@Value("${jdbc.generateDdl}")
	private String jdbcGenerateddl;
	@Value("${jdbc.showSql}")
	private String jdbcShowsql;
	@Value("${jdbc.databasePlatform}")
	private String jdbcDatabaseplatform;
	@Value("${jdbc.database}")
	private String jdbcDatabase;
	@Value("${database.embedded.scripts}")
	private String embeddedDatabaseScripts;
	
	@Bean
	public JpaTransactionManager transactionManager() {
		return new JpaTransactionManager();
	}
	
	@Conditional(value = EmbeddedDBCondition.class)
	@Bean
	public HibernateJpaVendorAdapter embeddedJpaVendorAdapter() {
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setDatabase(Database.H2);
		adapter.setShowSql(valueOf(jdbcShowsql));
		adapter.setGenerateDdl(valueOf(jdbcGenerateddl));
		return adapter;
	}
	
	@Conditional(value = DBServerCondition.class)
	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setDatabase(Enum.valueOf(Database.class, jdbcDatabase));
		adapter.setShowSql(valueOf(jdbcShowsql));
		adapter.setGenerateDdl(valueOf(jdbcGenerateddl));
		adapter.setDatabasePlatform(jdbcDatabaseplatform);
		return adapter;
	}
	
	@Conditional(value = DBServerCondition.class)
	@Bean
	public DataSource mySQLDataSource() {
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		driverManagerDataSource.setDriverClassName(jdbcDriverclassname);
		driverManagerDataSource.setUrl(jdbcUrl);
		driverManagerDataSource.setUsername(jdbcUsername);
		driverManagerDataSource.setPassword(jdbcPassword);
		return driverManagerDataSource;
	}
	
	@Conditional(value = EmbeddedDBCondition.class)
	@Bean
	public DataSource embeddedDataSource() {
		return new EmbeddedDatabaseBuilder()
				.addScripts(embeddedDatabaseScripts.split(","))
				.setScriptEncoding("utf-8")
				.setType(EmbeddedDatabaseType.H2)
				.build();
	}
	
	@Bean
	LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource);
		entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
		entityManagerFactoryBean.setPackagesToScan("hdfg159.chattogether.domain");
		return entityManagerFactoryBean;
	}
}
