package com.ftakas.dist.repository;

import com.ftakas.dist.domain.PropertyVal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface PropertyValRepository<T extends PropertyVal> extends CrudRepository<T, Long> {
}
