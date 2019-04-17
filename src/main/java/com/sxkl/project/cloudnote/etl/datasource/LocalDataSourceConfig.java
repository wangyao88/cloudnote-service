package com.sxkl.project.cloudnote.etl.datasource;


import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.sxkl.project.cloudnote.etl.mapper.local", sqlSessionTemplateRef = "localSqlSessionTemplate")
public class LocalDataSourceConfig {

    @Bean(name = "localDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.local.hikari")
    public HikariDataSource localDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        return dataSource;
    }

    @Bean(name = "localSqlSessionFactory")
    public SqlSessionFactory localSqlSessionFactory(@Qualifier("localDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //加载其他文件，如mapper.xml
        // bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/local1/*.xml"));
        return bean.getObject();
    }

    //事务管理
    @Bean(name = "localTransactionManager")
    public DataSourceTransactionManager localTransactionManager(@Qualifier("localDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "localSqlSessionTemplate")
    public SqlSessionTemplate localSqlSessionTemplate(@Qualifier("localSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
