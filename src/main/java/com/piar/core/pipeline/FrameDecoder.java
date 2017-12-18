package com.piar.core.pipeline;

import com.piar.entity.CodeEvent;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;

/**
 * Created by jnkmhbl on 17/3/8.
 *
 * checkCode 2 byte |  1byte empty | unsignedInt mark the length | content | int crc code
 */
public class FrameDecoder extends org.jboss.netty.handler.codec.frame.FrameDecoder {
    public static final int FRONT_LENGTH = 3; //标记content长度
    public static final int BODY_LENGTH = 4;
    public static final int HEAD_LENGTH = FRONT_LENGTH + BODY_LENGTH ;
    public static final int TAIL_LENGTH = 4 ; //crc校验码长度
    public static final int CHECK_LENGTH = 2 ; //校验码长度 ,用来校验是不是pair消息
    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer)
            throws Exception {
        Object message = null ;
        //这里不直接判断 < headLength 是因为要排除非法字符
        if(buffer.readableBytes() < CHECK_LENGTH){
            return message ;
        }

        //校验是否是pair消息
        byte[] headMsgs = new byte[2];
        buffer.getBytes(buffer.readerIndex(), headMsgs);

        if(headMsgs[0] == 0x32 && headMsgs[1] == 0x64){
             return  doDecode(buffer);
        }else {
            throw new Exception("error msg type");
        }
    }
    private CodeEvent doDecode(ChannelBuffer buffer){
        CodeEvent event = null ;
        if(buffer.readableBytes() < HEAD_LENGTH){
            return event ;
        }
        int length =(int)buffer.getUnsignedInt(buffer.readerIndex() + FRONT_LENGTH);

        if(buffer.readableBytes() < length + HEAD_LENGTH){
            return event ;
        }

        return null ;
    }
}
