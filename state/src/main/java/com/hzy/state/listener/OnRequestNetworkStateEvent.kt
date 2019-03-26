package com.hzy.state.listener

import android.content.Context

/**
 * 请求获取当前网络状态
 * @author: ziye_huang
 * @date: 2019/3/22
 */
interface OnRequestNetworkStateEvent {

    /**
     * 获取当前网络状态
     * @param context
     * @return true 连接, false 未连接
     */
    fun onRequestNetworkState(context: Context): Boolean

}