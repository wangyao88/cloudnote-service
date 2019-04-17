package com.sxkl.project.cloudnote.etl.datasource;


import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.sxkl.project.cloudnote.etl.mapper.remote", sqlSessionTemplateRef = "remoteSqlSessionTemplate")
public class RemoteDataSourceConfig {

    @Bean(name = "remoteDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.remote.hikari")
    public HikariDataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        return dataSource;
    }

    @Bean(name = "remoteSqlSessionFactory")
    public SqlSessionFactory remoteSqlSessionFactory(@Qualifier("remoteDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //加载其他文件，如mapper.xml
        // bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/remote1/*.xml"));
        return bean.getObject();
    }

    //事务管理
    @Bean(name = "remoteTransactionManager")
    public DataSourceTransactionManager remoteTransactionManager(@Qualifier("remoteDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "remoteSqlSessionTemplate")
    public SqlSessionTemplate remoteSqlSessionTemplate(@Qualifier("remoteSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

//    @Bean(name = "remoteMyBatisCursorItemReader")
//    public MyBatisCursorItemReader myBatisCursorItemReader(@Qualifier("remoteSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
//        MyBatisCursorItemReader myBatisCursorItemReader = new MyBatisCursorItemReader();
//        myBatisCursorItemReader.setSqlSessionFactory(sqlSessionFactory);
//        myBatisCursorItemReader.setQueryId("com.sxkl.project.cloudnote.etl.mapper.remote.RemoteArticleMapper.findAll");
//        return myBatisCursorItemReader;
//    }
}
