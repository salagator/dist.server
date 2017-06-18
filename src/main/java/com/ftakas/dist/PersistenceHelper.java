package com.ftakas.dist;

import com.ftakas.dist.domain.Clazz;
import com.ftakas.dist.domain.DObject;
import com.ftakas.dist.domain.PropertyVal;
import com.ftakas.dist.domain.property.*;
import com.ftakas.dist.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Collection;

@Component
public class PersistenceHelper {
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

    @Autowired
    public PersistenceHelper(PropertyDefnRepository propertyDefnRepository,
                             BooleanPropertyValRepository booleanPropertyValRepository,
                             IntegerPropertyValRepository integerPropertyValRepository,
                             FloatingPointPropertyValRepository floatingPointPropertyValRepository,
                             DObjectPropertyValRepository dObjectPropertyValRepository,
                             PropertyDefnPropertyValRepository propertyDefnPropertyValRepository,
                             ClazzRepository clazzRepository,
                             DObjectRepository dObjectRepository,
                             ClazzPropertyValRepository clazzPropertyValRepository,
                             StringPropertyValRepository stringPropertyValRepository) {
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
    }

    private PropertyVal persistPropertyVal(PropertyVal propertyVal) {
        PropertyVal returnPropertyVal;
        switch (propertyVal.getPropertyType()) {
            case Boolean:
                BooleanPropertyVal booleanPropertyVal = (BooleanPropertyVal) propertyVal;
                returnPropertyVal = booleanPropertyValRepository.save(booleanPropertyVal);
                break;
            case DObject:
                DObjectPropertyVal dObjectPropertyVal = (DObjectPropertyVal) propertyVal;
                returnPropertyVal = dObjectPropertyValRepository.save(dObjectPropertyVal);
                break;
            case Clazz:
                ClazzPropertyVal clazzPropertyVal = (ClazzPropertyVal) propertyVal;
                returnPropertyVal = clazzPropertyValRepository.save(clazzPropertyVal);
                break;
            case FloatingPoint:
                FloatingPointPropertyVal floatingPointPropertyVal = (FloatingPointPropertyVal) propertyVal;
                returnPropertyVal = floatingPointPropertyValRepository.save(floatingPointPropertyVal);
                break;
            case Integer:
                IntegerPropertyVal integerPropertyVal = (IntegerPropertyVal) propertyVal;
                returnPropertyVal = integerPropertyValRepository.save(integerPropertyVal);
                break;
            case PropertyDefn:
                PropertyDefnPropertyVal propertyDefnPropertyVal = (PropertyDefnPropertyVal) propertyVal;
                returnPropertyVal = propertyDefnPropertyValRepository.save(propertyDefnPropertyVal);
                break;
            case String:
                StringPropertyVal stringPropertyVal = (StringPropertyVal) propertyVal;
                returnPropertyVal = stringPropertyValRepository.save(stringPropertyVal);
                break;
            default:
                throw new RuntimeException("Unknown property value type: " + propertyVal.getPropertyType().name());
        }
        return returnPropertyVal;
    }

    @Transactional
    public Clazz saveClazzAndItsProperties(Clazz clazz) {
        Collection<PropertyVal> properties = clazz.getProperties();
        for (PropertyVal propertyVal : properties) {
            persistPropertyVal(propertyVal);
        }
        return clazzRepository.save(clazz);
    }

    @Transactional
    public DObject saveDObjectAndItsProperties(DObject dObject) {
        Collection<PropertyVal> properties = dObject.getProperties();
        for (PropertyVal propertyVal : properties) {
            persistPropertyVal(propertyVal);
        }
        return dObjectRepository.save(dObject);
    }
}
