package com.start;

import com.database.mapper.UserMapper;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * Created by jim.huang on 2016/12/23.
 */
@Component
@org.springframework.context.annotation.Configuration
@MapperScan(basePackageClasses = {UserMapper.class})
class MybatisConfig {

    private final String mapper = "classpath:com/database/mapper/*Mapper.xml";
    private SqlSessionFactory sessionFactory;

    public MybatisConfig() {
    }

    @Bean
    public DataSource dataSource() {
        return DataSources.getDefault();
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        bean.setMapperLocations(resourcePatternResolver.getResources(mapper));
        bean.setDataSource(dataSource());
        return bean.getObject();
    }

    @Bean
    public Environment environment() {
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        DataSource source = DataSources.getDefault();
        Environment environment = new Environment("mysql", transactionFactory, source);
        return environment;
    }

    @Bean
   public DataSourceTransactionManager dataSourceTransactionManager() {
       DataSource dataSource = dataSource();
       return new DataSourceTransactionManager(dataSource);
   }
}
