package com.fengdai.qa.dao;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.fengdai.qa.constants.DataSourceConsts;

/**
 * 动态数据源配置
 * @author Lucian
 */
@Configuration
public class DynamicDataSourceConfiguration {
	// 精确到 master 目录，以便跟其他数据源隔离
    static final String MAPPER_LOCATION = "classpath:mybatis/mapper/*.xml";

    @Bean(name = DataSourceConsts.DEFAULT)
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.druid.master")
    public DataSource dataSourceMaster(){
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        dataSource.setName(DataSourceConsts.DEFAULT);
        return dataSource;
    }

    @Bean(name = DataSourceConsts.fengdai3)
    @ConfigurationProperties(prefix = "spring.datasource.druid.fengdai3")
    public DataSource dataSourceFengdai3(){
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        dataSource.setName(DataSourceConsts.fengdai3);
        return dataSource;
    }
    @Bean(name = DataSourceConsts.fengdai2)
    @ConfigurationProperties(prefix = "spring.datasource.druid.fengdai2")
    public DataSource dataSourceFengdai2(){
    	DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
    	dataSource.setName(DataSourceConsts.fengdai2);
    	return dataSource;
    }
    @Bean(name = DataSourceConsts.fengdai3online)
    @ConfigurationProperties(prefix = "spring.datasource.druid.fengdai3online")
    public DataSource dataSourceFengdai3online(){
    	DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
    	dataSource.setName(DataSourceConsts.fengdai3online);
    	return dataSource;
    }

    /**
     * 动态数据源: 通过AOP在不同数据源之间动态切换
     * @return
     */
    @Bean(name = "dynamicDataSource")
    public DynamicDataSource dataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        // 默认数据源
        dynamicDataSource.setDefaultTargetDataSource(dataSourceMaster());

        // 配置多数据源
        Map<Object, Object> dsMap = new HashMap(10);
        dsMap.put(DataSourceConsts.DEFAULT, dataSourceMaster());
        dsMap.put(DataSourceConsts.fengdai2, dataSourceFengdai2());
        dsMap.put(DataSourceConsts.fengdai3, dataSourceFengdai3());
        dsMap.put(DataSourceConsts.fengdai3online, dataSourceFengdai3online());
        dynamicDataSource.setTargetDataSources(dsMap);
        return dynamicDataSource;
    }
    @Bean
    public PlatformTransactionManager txManager(@Qualifier("dynamicDataSource") DataSource dynamicDataSource) {
        return new DataSourceTransactionManager(dynamicDataSource);
    }
    @Bean
    @ConfigurationProperties(prefix = "mybatis")
    public SqlSessionFactoryBean sqlSessionFactoryBean(@Qualifier("dynamicDataSource")  DataSource dynamicDataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dynamicDataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(DynamicDataSourceConfiguration.MAPPER_LOCATION));
        return sqlSessionFactoryBean;
    }

    @Bean
    public JdbcTemplate testJdbcTemplate(@Qualifier("dynamicDataSource")  DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

//    @Bean
//    public SqlSessionFactory sqlSessionFactory(SqlSessionFactoryBean sqlSessionFactoryBean) throws Exception {
//        return sqlSessionFactoryBean.getObject();
//    }
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory); // 使用上面配置的Factory
        return template;
    }
}