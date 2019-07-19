package org.andot.jdatax;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;

/**
 * Hello JDataX
 * @author andot
 */

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class, DruidDataSourceAutoConfigure.class})
@ComponentScan({"org.andot.jdatax"})
public class JDataXApplication {
    public static void main( String[] args ){
        SpringApplication.run(JDataXApplication.class, args);
    }
}
