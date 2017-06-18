package com.ftakas.dist;

import com.ftakas.dist.domain.Clazz;
import com.ftakas.dist.domain.PropertyDefn;
import com.ftakas.dist.domain.PropertyType;
import com.ftakas.dist.repository.ClazzRepository;
import com.ftakas.dist.repository.PropertyDefnRepository;
import com.ftakas.dist.repository.StringPropertyValRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

@Component
public class InitialSystemLoad implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(InitialSystemLoad.class);

    private PropertyDefnRepository propertyDefnRepository;
    private ClazzRepository clazzRepository;
    private StringPropertyValRepository stringPropertyValRepository;

    @Autowired
    public InitialSystemLoad(PropertyDefnRepository propertyDefnRepository,
                             ClazzRepository clazzRepository,
                             StringPropertyValRepository stringPropertyValRepository) {
        this.propertyDefnRepository = propertyDefnRepository;
        this.clazzRepository = clazzRepository;
        this.stringPropertyValRepository = stringPropertyValRepository;
    }

    @Transactional
    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        long clazzCount = clazzRepository.count();
        if (clazzCount != 0) {
            logger.info("Initial System Load is a NOP because the system appears to have been initialized.");
            return;
        }

        logger.info("Starting Initial System Load.");

        PropertyDefn namePropertyDefn = new PropertyDefn();
        namePropertyDefn.setPropertyType(PropertyType.String);
        namePropertyDefn.setDefaultVal(null);
        namePropertyDefn.setArray(false);
        namePropertyDefn = propertyDefnRepository.save(namePropertyDefn);

        Collection<PropertyDefn> propertyDefnsForObject = new ArrayList<>();
        propertyDefnsForObject.add(namePropertyDefn);

        Clazz objectClazz = new Clazz();
        objectClazz.setSuperClazz(null);
        objectClazz.setPropertyDefns(propertyDefnsForObject);
        objectClazz = clazzRepository.save(objectClazz);

        logger.info("Initial System Load has been completed.");
    }
}
