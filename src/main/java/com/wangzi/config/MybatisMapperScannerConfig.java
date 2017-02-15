package com.wangzi.config;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * Mybatis扫描接口
 * @author Administrator
 *
 */
@Configuration
//由于MapperScannerConfigurer执行的比较早，所以必须有下面的注解
@AutoConfigureAfter(MybatisConfig.class)
public class MybatisMapperScannerConfig {
	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer(){
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
		mapperScannerConfigurer.setBasePackage("com.wangzi.dao");
		return mapperScannerConfigurer;
	}
}
