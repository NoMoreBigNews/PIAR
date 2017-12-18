package com.piar.provider;

/**
 * Created by jnkmhbl on 17/3/19.
 */
public class Request {
    private Class serviceInterface ;
    private Class[] paramClasses ;
    private Object[] params ;
    private String methodName ;

    public Class getServiceInterface() {
        return serviceInterface;
    }

    public void setServiceInterface(Class serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

    public Class[] getParamClasses() {
        return paramClasses;
    }

    public void setParamClasses(Class[] paramClasses) {
        this.paramClasses = paramClasses;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
