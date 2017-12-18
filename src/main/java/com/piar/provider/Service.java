package com.piar.provider;

/**
 * Created by jnkmhbl on 17/3/19.
 */
public interface Service {
    public boolean addService(ServiceProvider provider);
    public void removeService(ServiceProvider provider);
    public Response processRequest(Request request);
}
