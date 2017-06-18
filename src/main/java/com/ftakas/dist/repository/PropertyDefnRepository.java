package com.ftakas.dist.repository;

import com.ftakas.dist.domain.PropertyDefn;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface PropertyDefnRepository extends CrudRepository<PropertyDefn, Long> {
    List<PropertyDefn> findAllByName(String name);
}
