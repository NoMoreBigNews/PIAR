package com.piar.provider;


import com.piar.util.MethodKeyHelper;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jnkmhbl on 17/3/19.
 */
public class ThreadPoolProcessor {
    private Map<String,Method> methodCache;
    private Map<String,ServiceProvider> configMap;
    private Map<String,Object> lockMap ;
    private static Map<String,Object> blackMethodMap ;

    static {
        blackMethodMap = new HashMap<String, Object>();
        Class oc = Object.class;
        Method []methods = oc.getMethods();
        for(Method method : methods){
            blackMethodMap.put(method.getName(),"");
        }
    }

    public ThreadPoolProcessor(List<ServiceProvider> configs){
        if(configs == null || configs.size() == 0){
            throw new RuntimeException("empty config error");
        }
        for(ServiceProvider config : configs){
            configMap.put(config.getServiceInterface().getName(),config);
        }
        lockMap = new ConcurrentHashMap<String, Object>();
    }
    public Response process(Request request) throws Exception{
        if(request == null){
            return null ;
        }
        if(isExcludeMethod(request.getMethodName())){
            return null ;
        }
        String key = MethodKeyHelper.generateKey(request.getServiceInterface(), request.getMethodName(), request.getParamClasses());
        if(methodCache.get(key) == null){
            Object lock =lockMap.get(request.getServiceInterface().getName());
            synchronized (lock){
                if(methodCache.get(key) == null){
                    ServiceProvider provider = configMap.get(request.getServiceInterface().getName());
                    Class serviceInterface =  provider.getServiceInterface();
                    Method [] methods = serviceInterface.getMethods();
                    for(Method method : methods){
                        String methodKey = MethodKeyHelper.generateKey(serviceInterface, method.getName(), method.getParameterTypes());
                        methodCache.put(methodKey,method);
                    }
                }
            }
        }
        Method method = methodCache.get(key);
        ServiceProvider provider = configMap.get(request.getServiceInterface().getName());
        Object result = method.invoke(provider.getInstance(),request.getParams());

        return null ;
    }


    private  boolean isExcludeMethod(String method){
        if(blackMethodMap.containsKey(method)){
            return true ;
        }
        return false ;
    }
}

