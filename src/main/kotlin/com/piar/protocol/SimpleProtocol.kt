package com.piar.protocol

import java.io.Serializable

/**
 * Created by xingke on 2017/3/22.
 */

data class SimpleProtocol(val version: Long, val header: String, val rpcInvokation: RpcInvokation?): Serializable