package com.cyberway.core.utils.ajax;

import java.util.HashMap;
import java.util.Map;

/**
 * Buffalo Service Repository. This class hold all the services into 
 * this central place.
 *  
 * @author caiw
 * @version 1.0
 */
public class ServiceRepository {
    
    private Map repositoryMap;
    
    private static ServiceRepository defaultRepo = new ServiceRepository();
    
    public ServiceRepository() {
        repositoryMap = new HashMap();
    }
    
    /**
     * Get the default repository. In most cases, there are only one
     * repository. 
     * @return the default ServiceRepository
     */
    public static ServiceRepository getDefaultRepository() {
        return defaultRepo;
    }
    
    /**
     * Register one service to the repository
     * @param name The name of the service
     * @param serviceClass the class of the service
     */
    public void registerService(String name, Class serviceClass) {
        repositoryMap.put(name, serviceClass.getName());
    }
    
    public void registerService(String name, String serviceClass) {
        repositoryMap.put(name, serviceClass);
    }
    
    /**
     * Get the class full name
     */
    public String getService(String name) {
        return (String)repositoryMap.get(name);
    }
}
