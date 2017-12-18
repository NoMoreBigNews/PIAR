
package com.piar.server

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.piar.protocol.RpcInvokation
import com.piar.protocol.SimpleProtocol
import com.piar.protocol.SimpleProtocolDecoder
import com.piar.protocol.SimpleProtocolEncoder
import java.util.UUID
import io.netty.channel.socket.nio.NioSocketChannel
import com.sun.tools.internal.xjc.model.Multiplicity.group
import io.netty.bootstrap.Bootstrap
import io.netty.buffer.ByteBuf
import io.netty.channel.*
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.handler.codec.MessageToByteEncoder
import io.netty.util.CharsetUtil
import org.omg.CORBA.Object
import org.springframework.util.SerializationUtils
import java.io.ByteArrayOutputStream
import java.io.ObjectOutput
import java.io.ObjectOutputStream
import java.util.concurrent.TimeUnit
import java.io.IOException




/**
 * Created by xingke on 2017/3/22.
 */

fun sendMsgToServer(message: SimpleProtocol): java.lang.Object {
    var response: Any? = null
    val bootstrap = Bootstrap()
    val eventLoopGroup = NioEventLoopGroup()

    try {
        bootstrap.option(ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK,10 * 64 * 1024)
        bootstrap.option(ChannelOption.TCP_NODELAY, true)
        bootstrap
                .group(eventLoopGroup)
                .channel(NioSocketChannel::class.java)
                .handler(object : ChannelInitializer<Channel>() {
                    @Throws(Exception::class)
                    override fun initChannel(ch: Channel) {
                        ch.pipeline().addLast(SimpleProtocolEncoder()) // 编码器
                        ch.pipeline().addLast(object : SimpleChannelInboundHandler<ByteBuf>() {
                            override fun channelRead0(ctx: ChannelHandlerContext?, msg: ByteBuf) {

                                val protocolBytes = ByteArray(msg.readableBytes())
                                msg.readBytes(protocolBytes)

                                var simpleProtocol = SerializationUtils.deserialize(protocolBytes) as SimpleProtocol
                                response = simpleProtocol.rpcInvokation?.result
                                println("server_response: $response")
                            }
                        }) // 接收服务端数据
                    }
                })
        val channelFuture = bootstrap.connect("localhost", 9999).sync().channel()

        channelFuture.writeAndFlush(message)

        channelFuture.closeFuture().sync()

        return response as java.lang.Object
    } catch (e: Exception) {
        e.printStackTrace()
        throw RuntimeException("请求失败")
    } finally {
        try {
            eventLoopGroup.shutdownGracefully(1, 1, TimeUnit.SECONDS).sync()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}

fun methodToSimpleProtocol(service: String, methodName: String, paramTypes: Array<Class<*>>, args: Array<Any>): SimpleProtocol {

    return SimpleProtocol(1024L, UUID.randomUUID().toString(), RpcInvokation(service, methodName, paramTypes, args.map { it as java.lang.Object }.toTypedArray()))
}

fun deserializedString(input: String): JSONObject {
    var parseObject = JSON.parseObject(input)
    return parseObject
}

@Throws(IOException::class)
private fun convertToBytes(`object`: Any): ByteArray {
    ByteArrayOutputStream().use { bos ->
        ObjectOutputStream(bos).use { out ->
            out.writeObject(`object`)
            return bos.toByteArray()
        }
    }
}


fun main(args: Array<String>) {
    println("1")
}
