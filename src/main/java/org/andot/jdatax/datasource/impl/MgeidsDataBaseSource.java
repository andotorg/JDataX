package org.andot.jdatax.datasource.impl;

import java.io.IOException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import org.andot.jdatax.datasource.BaseDataSource;

@Configuration
@MapperScan(
		basePackages={"org.andot.jdatax.dao.mgeids"}, 
		sqlSessionFactoryRef="mgeidsSqlSessionFactory", 
		sqlSessionTemplateRef="mgeidsSqlSessionTemplate")
public class MgeidsDataBaseSource implements BaseDataSource {
	
	private static Logger log = LoggerFactory.getLogger(MgeidsDataBaseSource.class);

	@Bean("mgeidsDataSource")
	@Primary
	@ConfigurationProperties(prefix="spring.datasource.mgeids")
	@Override
	public DataSource baseDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean("mgeidsSqlSessionFactory")
	@Override
	public SqlSessionFactory baseSqlSessionFactory(@Qualifier("mgeidsDataSource") DataSource mgeidsDataSource) {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(mgeidsDataSource);
		try {
			sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/mgeids/*Mapper.xml"));
		} catch (IOException e) {
			log.error("加载mybatis配置文件出错，出错信息："+e.getMessage());
		}
		SqlSessionFactory sqlSessionFactory = null;
		try {
			sqlSessionFactory = sqlSessionFactoryBean.getObject();
		} catch (Exception e) {
			log.error("获取SqlSessionFactory失败，出错信息："+e.getMessage());
		}
		return sqlSessionFactory;
	}

	@Bean("mgeidsDataSourceTransactionManager")
	@Override
	public DataSourceTransactionManager baseDataSourceTransactionManager(@Qualifier("mgeidsDataSource") DataSource mgeidsDataSource) {
		return new DataSourceTransactionManager(mgeidsDataSource);
	}

	@Bean("mgeidsSqlSessionTemplate")
	@Override
	public SqlSessionTemplate baseSqlSessionTemplate(@Qualifier("mgeidsSqlSessionFactory") SqlSessionFactory mgeidsSqlSessionFactory) {
		return new SqlSessionTemplate(mgeidsSqlSessionFactory);
	}

}
