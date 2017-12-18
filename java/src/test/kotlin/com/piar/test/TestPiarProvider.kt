package com.piar.test

import com.piar.demo.TestLocalService
import com.piar.server.SimpleServer
import org.springframework.context.support.ClassPathXmlApplicationContext

/**
 * Created by xingke on 2017/3/23.
 */
class TestPiarProvider {
}

fun start() {
    var context = ClassPathXmlApplicationContext("classpath:test-provider.xml")
    context.start()
    var server = context.getBean("server") as SimpleServer
    server.startNettyServer()
}


fun main(args: Array<String>) {
    start()
}
