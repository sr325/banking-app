package com.monese.bank.config;

import com.monese.bank.impl.AccountImpl;
import com.monese.bank.impl.TransactionImp;
import com.monese.bank.repository.IAccount;
import com.monese.bank.repository.ITransaction;
import com.monese.bank.service.TransactionService;
import org.hibernate.engine.transaction.internal.TransactionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource(value = {"classpath:application.properties"})
public class DatabaseConfig {

    @Bean
    public DataSource dataSource(@Value("${spring.datasource.url}") String url,
                                 @Value("${spring.datasource.username}") String username,
                                 @Value("${spring.datasource.password}") String password,
                                 @Value("${database.driver}") String driver) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setUrl(url);
        return dataSource;
    }

    @Bean
    @Autowired

    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
                                                                       @Qualifier("jpaProperties") Properties jpaProps) {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaProperties(jpaProps);
        em.setPackagesToScan("com.monese.bank.model");
        return em;
    }


    @Bean
    @Autowired
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @Bean(name = "jpaProperties")
    public Properties sqlJpaProperties(@Value("${spring.jpa.hibernate.ddl-auto}") String ddlAuto,
                                       @Value("${spring.jpa.properties.hibernate.dialect}") String dialect,
                                       @Value("${spring.jpa.hibernate.naming-strategy}") String namingStrategy) {
        Properties jpaProperties = new Properties();
        jpaProperties.setProperty("hibernate.dialect", dialect);
        jpaProperties.setProperty("hibernate.hbm2ddl.auto", ddlAuto);
        jpaProperties.setProperty("hibernate.ejb.naming_strategy", namingStrategy);
        jpaProperties.setProperty("hibernate.show_sql", "false");
        jpaProperties.setProperty("hibernate.generate_statistics", "false");
        jpaProperties.setProperty("hibernate.use_sql_commands", "false");
        jpaProperties.setProperty("hibernate.format_sql", "false");
        return jpaProperties;
    }

    @Bean
    public IAccount oracleAccount() {
        return new AccountImpl();
    }

    @Bean
    public ITransaction oracleTransaction(){
        return new TransactionImp();
    }
}
