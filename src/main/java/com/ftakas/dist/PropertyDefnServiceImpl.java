package com.ftakas.dist;

import com.ftakas.dist.domain.PropertyDefn;
import com.ftakas.dist.props.PropertyDefnService;
import com.ftakas.dist.repository.PropertyDefnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyDefnServiceImpl implements PropertyDefnService {
    private PropertyDefnRepository propertyDefnRepository;

    @Autowired
    public PropertyDefnServiceImpl(PropertyDefnRepository propertyDefnRepository) {
        this.propertyDefnRepository = propertyDefnRepository;
    }

    @Override
    @Cacheable("propertyDefns")
    public PropertyDefn getPropertyDefn(String name) {
        List<PropertyDefn> propertyDefnList = propertyDefnRepository.findAllByName(name);
        if (propertyDefnList.size() > 1) {
            throw new RuntimeException("Looking for a property definition with name: '" + name +
                    "', returned multiple results (" + propertyDefnList.size() + ").");
        }
        return propertyDefnList.size() == 1 ? propertyDefnList.get(0) : null;
    }
}
