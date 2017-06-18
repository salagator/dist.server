package com.ftakas.dist;

import com.ftakas.dist.domain.*;
import com.ftakas.dist.domain.property.*;
import com.ftakas.dist.props.PropertyDefnService;
import com.ftakas.dist.props.Props;
import com.ftakas.dist.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class InitialSystemLoad implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(InitialSystemLoad.class);

    private PropertyDefnRepository propertyDefnRepository;
    private BooleanPropertyValRepository booleanPropertyValRepository;
    private IntegerPropertyValRepository integerPropertyValRepository;
    private FloatingPointPropertyValRepository floatingPointPropertyValRepository;
    private DObjectPropertyValRepository dObjectPropertyValRepository;
    private PropertyDefnPropertyValRepository propertyDefnPropertyValRepository;
    private ClazzRepository clazzRepository;
    private DObjectRepository dObjectRepository;
    private ClazzPropertyValRepository clazzPropertyValRepository;
    private StringPropertyValRepository stringPropertyValRepository;
    private PropertyDefnService propertyDefnService;
    private PersistenceHelper persistenceHelper;

    @Autowired
    public InitialSystemLoad(PropertyDefnRepository propertyDefnRepository,
                             BooleanPropertyValRepository booleanPropertyValRepository,
                             IntegerPropertyValRepository integerPropertyValRepository,
                             FloatingPointPropertyValRepository floatingPointPropertyValRepository,
                             DObjectPropertyValRepository dObjectPropertyValRepository,
                             PropertyDefnPropertyValRepository propertyDefnPropertyValRepository,
                             ClazzRepository clazzRepository,
                             DObjectRepository dObjectRepository,
                             ClazzPropertyValRepository clazzPropertyValRepository,
                             StringPropertyValRepository stringPropertyValRepository,
                             PropertyDefnService propertyDefnService,
                             PersistenceHelper persistenceHelper) {
        this.propertyDefnRepository = propertyDefnRepository;
        this.booleanPropertyValRepository = booleanPropertyValRepository;
        this.integerPropertyValRepository = integerPropertyValRepository;
        this.floatingPointPropertyValRepository = floatingPointPropertyValRepository;
        this.dObjectPropertyValRepository = dObjectPropertyValRepository;
        this.propertyDefnPropertyValRepository = propertyDefnPropertyValRepository;
        this.clazzRepository = clazzRepository;
        this.dObjectRepository = dObjectRepository;
        this.clazzPropertyValRepository = clazzPropertyValRepository;
        this.stringPropertyValRepository = stringPropertyValRepository;
        this.propertyDefnService = propertyDefnService;
        this.persistenceHelper = persistenceHelper;
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
        namePropertyDefn.setName("name");
        namePropertyDefn.setDefaultVal(null);
        namePropertyDefn.setArray(false);
        propertyDefnRepository.save(namePropertyDefn);

        PropertyDefn superClazzPropertyDefn = new PropertyDefn();
        superClazzPropertyDefn.setPropertyType(PropertyType.Clazz);
        superClazzPropertyDefn.setName("super");
        superClazzPropertyDefn.setDefaultVal(null);
        superClazzPropertyDefn.setArray(false);
        propertyDefnRepository.save(superClazzPropertyDefn);

        Props props = new Props(null, null, propertyDefnService);

        Clazz metaClazz = props.createNewClazz("Meta", null);
        metaClazz = persistenceHelper.saveClazzAndItsProperties(metaClazz);

        Clazz objectClazz = props.createNewClazz("Object", metaClazz);
        objectClazz = persistenceHelper.saveClazzAndItsProperties(objectClazz);

        DObject firstDObject = props.createNewDObject("object instance", objectClazz);
        persistenceHelper.saveDObjectAndItsProperties(firstDObject);

        logger.info("Initial System Load has been completed.");
    }
}
