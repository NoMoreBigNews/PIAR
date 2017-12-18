package com.piar.protocol

import java.io.ObjectOutput
import java.io.Serializable

/**
 * Created by xingke on 2017/3/24.
 */
data class RpcInvokation(val service: String, val methodName: String, val parameterTypes: Array<Class<*>>, val args: Array<Object>): Serializable {

    var result: Any? = null
    constructor(result: Any): this("", "", arrayOf(1.javaClass), arrayOf(1 as Object)) {
        this.result = result
    }

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }


    override fun hashCode(): Int {
        return super.hashCode()
    }
}