package com.hzy.state.controller

/**
 * ViewState 全局配置管理者
 * @author: ziye_huang
 * @date: 2019/3/22
 */
class ViewStateManager private constructor() : ViewStateProviderImpl<ViewStateManager>() {

    companion object {

        private var sInstance: ViewStateManager? = null
            get() {
                if (field == null) {
                    field = ViewStateManager()
                }
                return field
            }

        @Synchronized
        fun getInstance(): ViewStateManager {
            return sInstance!!
        }
    }

}