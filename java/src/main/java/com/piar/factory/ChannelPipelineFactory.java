package com.piar.factory;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.Channels;
import com.piar.core.pipeline.FrameDecoder;
import com.piar.core.pipeline.FrameEncoder;

/**
 * Created by jnkmhbl on 17/3/6.
 */
public class ChannelPipelineFactory  implements org.jboss.netty.channel.ChannelPipelineFactory {
    public ChannelPipeline getPipeline() {
        ChannelPipeline pipeline = Channels.pipeline();
        pipeline.addLast("encoder",new FrameEncoder());
        pipeline.addLast("decoder",new FrameDecoder());
        return pipeline ;
    }
}
