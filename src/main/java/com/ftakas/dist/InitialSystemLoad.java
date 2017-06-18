package com.ftakas.dist;

import com.ftakas.dist.domain.*;
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
    private ClazzRepository clazzRepository;
    private DObjectRepository dObjectRepository;
    private ClazzPropertyValRepository clazzPropertyValRepository;
    private StringPropertyValRepository stringPropertyValRepository;

    @Autowired
    public InitialSystemLoad(PropertyDefnRepository propertyDefnRepository,
                             ClazzRepository clazzRepository,
                             DObjectRepository dObjectRepository,
                             ClazzPropertyValRepository clazzPropertyValRepository,
                             StringPropertyValRepository stringPropertyValRepository) {
        this.propertyDefnRepository = propertyDefnRepository;
        this.clazzRepository = clazzRepository;
        this.dObjectRepository = dObjectRepository;
        this.clazzPropertyValRepository = clazzPropertyValRepository;
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

        Collection<PropertyDefn> propertyDefnsForMetaClazz = new ArrayList<>();
        propertyDefnsForMetaClazz.add(namePropertyDefn);

        Clazz metaClazz = new Clazz();
        metaClazz.setPropertyDefns(propertyDefnsForMetaClazz);
        metaClazz.setProperties(new ArrayList<>());
        metaClazz = clazzRepository.save(metaClazz);

        PropertyDefn superClazzPropertyDefn = new PropertyDefn();
        superClazzPropertyDefn.setPropertyType(PropertyType.Clazz);
        superClazzPropertyDefn.setDefaultVal(null);
        superClazzPropertyDefn.setArray(false);
        superClazzPropertyDefn = propertyDefnRepository.save(superClazzPropertyDefn);

        Collection<PropertyDefn> propertyDefnsForObjectClazz = new ArrayList<>();
        propertyDefnsForObjectClazz.add(namePropertyDefn);
        propertyDefnsForObjectClazz.add(superClazzPropertyDefn);

        ClazzPropertyVal objectSuperClazzPropertyVal = new ClazzPropertyVal();
        objectSuperClazzPropertyVal.setPropertyDefn(superClazzPropertyDefn);
        objectSuperClazzPropertyVal.setDirty(false);
        objectSuperClazzPropertyVal.setInConflict(false);
        List<Clazz> superClazzList = new ArrayList<>();
        superClazzList.add(metaClazz);
        objectSuperClazzPropertyVal.setClazzList(superClazzList);
        objectSuperClazzPropertyVal = clazzPropertyValRepository.save(objectSuperClazzPropertyVal);

        StringPropertyVal objectNamePropertyVal = new StringPropertyVal();
        objectNamePropertyVal.setPropertyDefn(namePropertyDefn);
        objectNamePropertyVal.setDirty(false);
        objectNamePropertyVal.setInConflict(false);
        List<String> objectClassNameList = new ArrayList<>();
        objectClassNameList.add("Object");
        objectNamePropertyVal.setStringList(objectClassNameList);
        objectNamePropertyVal = stringPropertyValRepository.save(objectNamePropertyVal);

        Collection<PropertyVal> propertiesForObjectClazz = new ArrayList<>();
        propertiesForObjectClazz.add(objectSuperClazzPropertyVal);
        propertiesForObjectClazz.add(objectNamePropertyVal);

        Clazz objectClazz = new Clazz();
        objectClazz.setPropertyDefns(propertyDefnsForObjectClazz);
        objectClazz.setProperties(propertiesForObjectClazz);
        objectClazz = clazzRepository.save(objectClazz);

        ClazzPropertyVal firstObjectClazzPropertyVal = new ClazzPropertyVal();
        firstObjectClazzPropertyVal.setPropertyDefn(superClazzPropertyDefn);
        firstObjectClazzPropertyVal.setDirty(false);
        firstObjectClazzPropertyVal.setInConflict(false);
        List<Clazz> clazzList = new ArrayList<>();
        clazzList.add(objectClazz);
        firstObjectClazzPropertyVal.setClazzList(clazzList);
        firstObjectClazzPropertyVal = clazzPropertyValRepository.save(firstObjectClazzPropertyVal);

        StringPropertyVal firstObjectNamePropertyVal = new StringPropertyVal();
        firstObjectNamePropertyVal.setPropertyDefn(namePropertyDefn);
        firstObjectNamePropertyVal.setDirty(false);
        firstObjectNamePropertyVal.setInConflict(false);
        List<String> objectNameList = new ArrayList<>();
        objectNameList.add("object instance");
        firstObjectNamePropertyVal.setStringList(objectNameList);
        firstObjectNamePropertyVal = stringPropertyValRepository.save(firstObjectNamePropertyVal);

        Collection<PropertyVal> propertiesForFirstObject = new ArrayList<>();
        propertiesForFirstObject.add(firstObjectClazzPropertyVal);
        propertiesForFirstObject.add(firstObjectNamePropertyVal);

        DObject firstDObject = new DObject();
        firstDObject.setProperties(propertiesForFirstObject);
        dObjectRepository.save(firstDObject);

        logger.info("Initial System Load has been completed.");
    }
}
