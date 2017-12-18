package com.piar.core.pipeline;

import com.piar.entity.CodeEvent;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.DynamicChannelBuffer;
import org.jboss.netty.channel.*;

import java.util.zip.Adler32;

/**
 * Created by jnkmhbl on 17/3/8.
 */
public class Crc32Handler extends SimpleChannelHandler{
    //线程不安全，所以使用threadLocal
    private static ThreadLocal<Adler32> adler32Map = new ThreadLocal<Adler32>();

    @Override
    public void messageReceived(ChannelHandlerContext ctx,MessageEvent e)throws Exception{
        if(e.getMessage() == null || (!(e.getMessage() instanceof CodeEvent))){
            return ;
        }
        CodeEvent event = (CodeEvent)e.getMessage() ;
        ChannelBuffer buffer = event.getBuffer() ;

    }

    @Override
    public void handleDownstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception
    {
        if(!(e instanceof MessageEvent)){
            ctx.sendDownstream(e);
            return  ;
        }
        MessageEvent msgEvent =(MessageEvent)e;
        if(!(msgEvent.getMessage() instanceof CodeEvent)){
            ctx.sendDownstream(msgEvent);
            return ;
        }
        CodeEvent codeEvent = (CodeEvent)msgEvent ;
        if(codeEvent.isUnified()){
            ctx.sendDownstream(e);
            return ;
        }
        ChannelBuffer frame = codeEvent.getBuffer();
        int checkSum = (int)doChecksum0(frame,frame.readableBytes());
        if(frame instanceof DynamicChannelBuffer){
            ChannelBuffer buffer = frame.factory().getBuffer(frame.readableBytes() + 4);
            buffer.writeInt(checkSum);
            frame = buffer ;
            Channels.write(ctx,msgEvent.getFuture(),((MessageEvent) e).getRemoteAddress());
        }



    }
    private boolean doUnCheckSum(Channel channel,CodeEvent event){
        ChannelBuffer frame = event.getBuffer();
        int dataLength = frame.readableBytes() - 4;

        //写入数据
        ChannelBuffer  dataBuffer = frame.factory().getBuffer(dataLength);
        dataBuffer.writeBytes(frame, frame.readerIndex(), dataLength);
        int checkSum = (int) doChecksum0(dataBuffer,dataLength);

        int _checkSum = frame.getInt(dataLength);
        if(checkSum == _checkSum){
            event.setBuffer(dataBuffer);
            event.setCrc32Checked(true);
            event.setValid(true);
            return true ;
        }
        return false ;

    }

    private long doChecksum0(ChannelBuffer frame, int frameLength) {
        Adler32 adler32 = adler32Map.get();
        if (adler32 == null) {
            adler32 = new Adler32();
            adler32Map.set(adler32);
        }
        adler32.reset();

        adler32.update(frame.array(), 0, frameLength);
        return adler32.getValue();
    }
}
