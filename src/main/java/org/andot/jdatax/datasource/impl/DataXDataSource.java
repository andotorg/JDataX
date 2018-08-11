package org.andot.jdatax.datasource.impl;

import java.io.IOException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@MapperScan(
	basePackages="org.andot.jdatax.dao.base", 
	sqlSessionFactoryRef="baseSqlSessionFactory", 
	sqlSessionTemplateRef="baseSqlSessionTemplate"
)
public class DataXDataSource {

	@Bean("baseDataSource")
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource.base")
	public DataSource baseDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean("baseSqlSessionFactory")
	@Primary
	public SqlSessionFactory baseSqlSessionFactory(@Qualifier("baseDataSource") DataSource baseDataSource) {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(baseDataSource);
		try {
			bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/base/*Mapper.xml"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		SqlSessionFactory sqlSessionFactory = null;
		try {
			sqlSessionFactory = bean.getObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sqlSessionFactory;
	}

	@Bean("baseDataSourceTransactionManager")
	@Primary
	public DataSourceTransactionManager baseDataSourceTransactionManager(@Qualifier("baseDataSource") DataSource baseDataSource) {
		return new DataSourceTransactionManager(baseDataSource);
	}

	@Bean("baseSqlSessionTemplate")
	@Primary
	public SqlSessionTemplate baseSqlSessionTemplate(@Qualifier("baseSqlSessionFactory") SqlSessionFactory baseSqlSessionFactory) {
		return new SqlSessionTemplate(baseSqlSessionFactory);
	}
	
}
