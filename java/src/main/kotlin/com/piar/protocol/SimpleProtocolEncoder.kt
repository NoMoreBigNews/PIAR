package com.piar.protocol

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelFutureListener
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder
import org.springframework.util.SerializationUtils

/**
 * Created by xingke on 2017/3/24.
 */

class SimpleProtocolEncoder: MessageToByteEncoder<SimpleProtocol>() {

    override fun encode(ctx: ChannelHandlerContext?, msg: SimpleProtocol, out: ByteBuf) {
        out.writeLong(msg.version)
        out.writeBytes(msg.header.toByteArray())
        var serialize = SerializationUtils.serialize(msg.rpcInvokation)
        out.writeBytes(serialize)
    }

}
