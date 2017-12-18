package com.piar.provider;

/**
 * Created by jnkmhbl on 17/3/19.
 */
public class ServiceProvider {
    private Class  serviceInterface ;
    private Object instance ;
    private ProviderConfig config ;


    public Class getServiceInterface() {
        return serviceInterface;
    }

    public void setServiceInterface(Class serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

    public Object getInstance() {
        return instance;
    }

    public void setInstance(Object instance) {
        this.instance = instance;
    }

    public ProviderConfig getConfig() {
        return config;
    }

    public void setConfig(ProviderConfig config) {
        this.config = config;
    }
}
