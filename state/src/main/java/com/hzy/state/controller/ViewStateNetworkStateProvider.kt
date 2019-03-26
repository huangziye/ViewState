package com.hzy.state.controller

import android.content.Context
import com.hzy.state.listener.OnRequestNetworkStateEvent

/**
 * ViewState 控制器网络状态提供者
 * @author: ziye_huang
 * @date: 2019/3/22
 */
class ViewStateNetworkStateProvider private constructor() {

    private var mNetworkStateEvent: OnRequestNetworkStateEvent? = null

    companion object {
        private var mInstance: ViewStateNetworkStateProvider? = null
            get() {
                if (field == null) {
                    field = ViewStateNetworkStateProvider()
                }
                return field
            }

        /**
         * 需要线程安全的时候可以加上 @Synchronized
         */
        @Synchronized
        fun getInstance(): ViewStateNetworkStateProvider {
            return mInstance!!
        }
    }

    fun isConnected(context: Context): Boolean {
        return null == mNetworkStateEvent || mNetworkStateEvent!!.onRequestNetworkState(context)
    }

    fun registerOnRequestNetworkStateEvent(networkStateEvent: OnRequestNetworkStateEvent) {
        mNetworkStateEvent = networkStateEvent
    }

}