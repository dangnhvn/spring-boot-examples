package com.example.icommerce.configurations;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("com.example.icommerce.repositories")
public class DatabaseConfiguration {

    @Bean("dataSource")
    @ConfigurationProperties("spring.datasource")
    public DataSource dataSource () {
        return DataSourceBuilder.create().build();
    }

    @Bean("entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
        Environment env, @Qualifier("dataSource") DataSource dataSource
    ) {

        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource);
        entityManagerFactory.setPackagesToScan(
            env.getProperty("spring.jpa.package-to-scan", "").split("\\s*,\\s*")
        );

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactory.setJpaVendorAdapter(vendorAdapter);

        Properties hibernateProperties = new Properties();
        hibernateProperties.put(
            "hibernate.id.new_generator_mappings",
            env.getProperty("spring.jpa.hibernate.use-new-id-generator-mappings", Boolean.class, false)
        );
        hibernateProperties.put(
            "hibernate.show_sql",
            env.getProperty("spring.jpa.show-sql", Boolean.class, false)
        );
        hibernateProperties.put(
            "hibernate.hbm2ddl.auto",
            env.getRequiredProperty("spring.jpa.hibernate.ddl-auto")
        );
        hibernateProperties.put(
            "hibernate.connection.CharSet",
            env.getRequiredProperty("spring.jpa.properties.hibernate.connection.CharSet")
        );
        hibernateProperties.put(
            "hibernate.connection.characterEncoding",
            env.getRequiredProperty("spring.jpa.properties.hibernate.connection.characterEncoding")
        );
        hibernateProperties.put(
            "hibernate.connection.useUnicode",
            env.getRequiredProperty("spring.jpa.properties.hibernate.connection.useUnicode")
        );
        entityManagerFactory.setJpaProperties(hibernateProperties);

        entityManagerFactory.setPersistenceUnitPostProcessors(pui -> {
            if ( pui.getManagedClassNames() != null && !pui.getManagedClassNames().isEmpty() ) {
                pui.getManagedClassNames().removeIf(className -> className.contains("Credential"));
            }
        });

        return entityManagerFactory;
    }

    @Bean("transactionManager")
    public PlatformTransactionManager transactionManager (EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);

        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTransaction () {
        return new PersistenceExceptionTranslationPostProcessor();
    }
}
