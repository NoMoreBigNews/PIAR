package com.piar.server;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.piar.protocol.SimpleProtocol;
//import io.netty.channel.ChannelHandler;
//import io.netty.channel.ChannelHandlerContext;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//
//import java.lang.reflect.Method;
//
//import static com.piar.server.SimpleClientKt.deserializedString;
//
///**
// * Created by xingke on 2017/3/24.
// */
//@Component
//@ChannelHandler.Sharable
public class JavaServerHandler {

    public static void casts(Class clazz, Object value) {

        if (clazz.isInstance(int.class)) {
            System.out.println(Integer.class.cast(value));
        }

    }

    public static void main(String[] args0) {
        casts(int.class, 10);
    }

//    @Autowired
//    private ApplicationContext appContext;
//
//    @Override
//    public void channelRead0(ChannelHandlerContext ctx, SimpleProtocol msg) throws ClassNotFoundException {
//        JSONObject jobj = deserializedString(msg.getContent());
//        String serviceName = jobj.getString("service");
//        String methodName = jobj.getString("method");
//        JSONArray args = jobj.getJSONArray("args");
//
//        Object bean = appContext.getBean(appContext.getBeanNamesForType(Class.forName(serviceName))[0]);
//        Method[] methods = bean.getClass().getDeclaredMethods();
//
//        Method method = methods[0];
//
//        int a = 0;
//
//
//        var realArgs = args.zip(method.parameterTypes).map {
//            it.second.componentType.cast(it.first)
//        }.toTypedArray()
//
//
//        Object invoke = method.invoke(bean, realArgs)
//        println("服务为: $serviceName")
//        println("参数为: $jobj['args']")
//        ctx.writeAndFlush(Unpooled.copiedBuffer("100", CharsetUtil.UTF_8)).addListener(ChannelFutureListener.CLOSE)
//    }
//
}
