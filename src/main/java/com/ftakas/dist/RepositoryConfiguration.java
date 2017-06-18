package com.ftakas.dist;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.ftakas.dist.domain"})
@EnableJpaRepositories(basePackages = {"com.ftakas.dist.repository"})
public class RepositoryConfiguration {
}