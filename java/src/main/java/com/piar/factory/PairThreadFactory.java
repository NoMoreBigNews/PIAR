package com.piar.factory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by jnkmhbl on 17/3/6.
 */
public class PairThreadFactory implements ThreadFactory {
    private final AtomicInteger threadNumCounter = new AtomicInteger(1);
    private static final AtomicInteger POOL_SEQ = new AtomicInteger(1);
    private final ThreadGroup threadGroup ;
    private final String threadPrefix ="pair" ;
    private boolean deamon = true ;
    public PairThreadFactory(){
        this("pair-" + POOL_SEQ.getAndIncrement(), true);
    }
    public PairThreadFactory(String prefix, boolean daemo) {
        prefix = prefix + "-thread-";
        deamon = daemo;
        SecurityManager s = System.getSecurityManager();
        threadGroup = (s == null) ? Thread.currentThread().getThreadGroup() : s.getThreadGroup();
    }

    public Thread newThread(Runnable runnable) {
        String name = threadPrefix + threadNumCounter.getAndIncrement();
        Thread thread = new Thread(threadGroup, runnable, name, 0);
        thread.setDaemon(deamon);
        return thread;
    }
}
