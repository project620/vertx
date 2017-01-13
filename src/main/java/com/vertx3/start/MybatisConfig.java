package com.vertx3.start;

import javax.sql.DataSource;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.vertx3.database.mapper.UserMapper;

/**
 * Created by jim.huang on 2016/12/23.
 */
@Component
@org.springframework.context.annotation.Configuration
@EnableTransactionManagement
@MapperScan(basePackageClasses = {UserMapper.class})
class MybatisConfig {

    @Autowired
    DataSource dataSource;
    private final String mapper = "classpath:com/vertx3/database/mapper/*Mapper.xml";

    public MybatisConfig() {
    }

    @Bean
    public DataSource dataSource() {
        return DataSources.getDefault();
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        final SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        final ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        bean.setMapperLocations(resourcePatternResolver.getResources(mapper));
        bean.setDataSource(dataSource);
        return bean.getObject();
    }

    @Bean
    public Environment environment() {
        final TransactionFactory transactionFactory = new JdbcTransactionFactory();
        final Environment environment = new Environment("dataSource", transactionFactory, dataSource);
        return environment;
    }

    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }
}
