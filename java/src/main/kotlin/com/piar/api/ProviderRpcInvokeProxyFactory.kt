package com.piar.api

import com.piar.demo.TestRemoteServiceImpl
import com.piar.server.sendMsgToServer
import java.lang.reflect.Proxy

/**
 * Created by xingke on 2017/3/23.
 */
class ProviderRpcInvokeProxyFactory {

    companion object {
        @JvmStatic
        fun <T> getProxy(clazz: Class<T>): T {
            val service = TestRemoteServiceImpl()
            return clazz.cast(
                    Proxy.newProxyInstance( service::class.java.classLoader, service::class.java.interfaces) {
                        proxy, method, args ->
                        var invoke = method.invoke(service, args)
                        invoke as Int
                    })
        }
    }

}