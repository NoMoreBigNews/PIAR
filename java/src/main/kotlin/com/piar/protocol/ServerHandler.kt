package com.piar.protocol

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.TypeReference
import com.piar.server.deserializedString
import io.netty.buffer.ByteBuf
import io.netty.buffer.EmptyByteBuf
import io.netty.channel.ChannelFutureListener
import io.netty.util.CharsetUtil
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.context.ApplicationContext
import org.springframework.util.SerializationUtils
import java.io.ObjectInputStream
import java.nio.ByteBuffer
import java.util.*


/**
 * Created by xingke on 2017/3/22.
 */
@Component
@ChannelHandler.Sharable
class ServerHandler : SimpleChannelInboundHandler<SimpleProtocol>() {

    @Autowired
    lateinit var appContext: ApplicationContext

    @Throws(Exception::class)
    override fun channelRead0(ctx: ChannelHandlerContext, msg: SimpleProtocol) {
        println("server receive: " + msg)

        var serviceName = msg.rpcInvokation!!.service
        var methodName = msg.rpcInvokation!!.methodName
        var parameterTypes = msg.rpcInvokation!!.parameterTypes
        var args = msg.rpcInvokation!!.args

        var bean = appContext.getBean(appContext.getBeanNamesForType(Class.forName(serviceName))[0])

        var method = bean::class.java.getMethod(methodName, *parameterTypes)

        var invoke = method.invoke(bean, *args)
        println("服务为: $serviceName")
        println("执行结果为: $invoke")

//        ctx.channel().write(msg)
//        ctx.writeAndFlush(invoke).addListener(ChannelFutureListener.CLOSE)
        var simpleProtocol = SimpleProtocol(1024L, UUID.randomUUID().toString(), RpcInvokation(result = invoke))

        ctx.writeAndFlush(Unpooled.copiedBuffer(SerializationUtils.serialize(simpleProtocol))).addListener(ChannelFutureListener.CLOSE)
    }
}