package com.piar.server;

import com.piar.factory.ChannelPipelineFactory;
import com.piar.factory.PairThreadFactory;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by jnkmhbl on 17/3/6.
 */
public class NettyServer {
    private String ip ;
    private int port ;
    private ServerBootstrap bootstrap ;
    private ChannelGroup channelGroup ;
    private Channel channel ;
    private volatile boolean  started = false ;
    private static ExecutorService bossExecutor = Executors.newSingleThreadExecutor(new PairThreadFactory(
            "Pair-Netty-Server-Boss", true));

    private static ExecutorService workerExecutor = Executors.newCachedThreadPool(new PairThreadFactory(
            "Pair-Netty-Server-Worker", true));
    private static final int workerCount =  Runtime.getRuntime().availableProcessors() * 2;
    private static ChannelFactory channelFactory = new NioServerSocketChannelFactory(bossExecutor, workerExecutor,
            workerCount);
    public NettyServer(){
        bootstrap = new ServerBootstrap(channelFactory);
        bootstrap.setPipelineFactory(new ChannelPipelineFactory());
        this.bootstrap.setOption("child.tcpNoDelay", true);
        this.bootstrap.setOption("child.keepAlive", true);
        this.bootstrap.setOption("child.reuseAddress", true);
        this.bootstrap.setOption("child.connectTimeoutMillis", 1000);
    }

    public static  void main(String args[])throws Exception{
        NettyServer server = new NettyServer();
        SocketAddress address = new InetSocketAddress("127.0.0.1",8080);
        server.bootstrap.bind(address);
        Thread.currentThread().join();
    }

}
