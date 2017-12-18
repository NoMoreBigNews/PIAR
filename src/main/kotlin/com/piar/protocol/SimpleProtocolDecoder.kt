package com.piar.protocol

import io.netty.buffer.ByteBuf
import io.netty.buffer.EmptyByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder
import org.springframework.util.SerializationUtils

/**
 * Created by xingke on 2017/3/22.
 */
class SimpleProtocolDecoder: ByteToMessageDecoder() {

    override fun decode(ctx: ChannelHandlerContext?, input: ByteBuf, out: MutableList<Any>) {
        try {
            val version = input.readLong() // 读取version

            val headerBytes = ByteArray(36)
            input.readBytes(headerBytes) // 读取header

            val header = String(headerBytes)

            val rpcInvocationBytes = ByteArray(input.readableBytes())
            input.readBytes(rpcInvocationBytes)

            var rpcInvocation = SerializationUtils.deserialize(rpcInvocationBytes) as RpcInvokation
            out.add(SimpleProtocol(version, header, rpcInvocation))
        } catch (e: Exception) {
            if (input !is EmptyByteBuf) {
                e.printStackTrace()
            }
        }

    }
}