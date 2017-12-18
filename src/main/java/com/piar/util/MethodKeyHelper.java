package com.piar.util;

/**
 * Created by jnkmhbl on 17/3/19.
 */
public class MethodKeyHelper {
    private static  ThreadLocal<StringBuilder> builderThreadLocal ;
    static {
        builderThreadLocal = new ThreadLocal<StringBuilder>();
    }

    //这种生成key的方式需要校验，序列化后的返回paramTypes是否和序列化之前一样
    public static String generateKey(Class inter,String method,Class[] paramTypes){
        StringBuilder builder = builderThreadLocal.get();
        if(builder == null){
           builderThreadLocal.set(new StringBuilder(128));
        }
        builder = builderThreadLocal.get();
        builder.delete(0,builder.capacity());
        builder.append(inter.getName() + "-");
        builder.append(method + "-");
        for(Class paramClass : paramTypes){
            builder.append(paramClass.getName());
        }
        return builder.toString();
    }
}
