package com.example.redissondemo;

import org.redisson.api.RedissonClient;
import org.redisson.spring.starter.RedissonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.redisson.Redisson;
import org.redisson.config.Config;


@Configuration
//@Import(RedissonAutoConfiguration.class)
public class RedisConfig {

//    @Bean
//    public RedissonClient redissonClient() {
//        Config config = new Config();
//        // 这里使用单节点配置，你可以根据需要配置集群、哨兵等模式
//        config.useSingleServer().setAddress("redis://localhost:6379");
//        // 根据Config对象创建RedissonClient实例
//        return Redisson.create(config);
//    }
}