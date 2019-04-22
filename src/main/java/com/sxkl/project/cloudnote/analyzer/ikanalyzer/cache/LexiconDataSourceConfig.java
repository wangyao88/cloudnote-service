package com.sxkl.project.cloudnote.analyzer.ikanalyzer.cache;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.sxkl.project.cloudnote.analyzer.ikanalyzer.cache", sqlSessionTemplateRef = "lexiconSqlSessionTemplate")
public class LexiconDataSourceConfig {

    @Bean(name = "lexiconDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.search.hikari")
    public HikariDataSource lexiconDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        return dataSource;
    }

    @Bean(name = "lexiconSqlSessionFactory")
    public SqlSessionFactory lexiconSqlSessionFactory(@Qualifier("lexiconDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        return bean.getObject();
    }

    @Bean(name = "lexiconTransactionManager")
    public DataSourceTransactionManager lexiconTransactionManager(@Qualifier("lexiconDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "lexiconSqlSessionTemplate")
    public SqlSessionTemplate lexiconSqlSessionTemplate(@Qualifier("lexiconSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
