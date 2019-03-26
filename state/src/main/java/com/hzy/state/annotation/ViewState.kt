package com.hzy.state.annotation

import androidx.annotation.IntDef

/**
 * 视图状态
 * @author: ziye_huang
 * @date: 2019/3/22
 */
@IntDef(
    ViewState.LOADING,
    ViewState.NETWORK_ERROR,
    ViewState.LOAD_ERROR,
    ViewState.EMPTY,
    ViewState.NOT_FOUND,
    ViewState.CONTENT,
    ViewState.WIDGET_NETWORK_ERROR,
    ViewState.WIDGET_ELFIN,
    ViewState.WIDGET_FLOAT
)
@Retention(AnnotationRetention.BINARY)
@MustBeDocumented
annotation class ViewState {
    companion object {
        /**
         * 加载中.
         */
        const val LOADING = 1
        /**
         * 网络错误.
         */
        const val NETWORK_ERROR = 2
        /**
         * 加载失败.
         */
        const val LOAD_ERROR = 3
        /**
         * 空布局.
         */
        const val EMPTY = 4
        /**
         * 未找到内容布局.
         */
        const val NOT_FOUND = 5
        /**
         * 内容.
         */
        const val CONTENT = 6
        /**
         * 网络错误小部件.
         * 一般会悬浮在Toolbar视图下方.
         * 视图层级上在ELFIN之下,其他状态之上.
         */
        const val WIDGET_NETWORK_ERROR = 7
        /**
         * 小精灵(提示布局).
         * 可以用来做像QQ聊天列表界面无网络时最近联系人列表顶部的网络提示布局,该布局会覆盖在其他所有视图上.
         */
        const val WIDGET_ELFIN = 8
        /**
         * 底部Float.
         */
        const val WIDGET_FLOAT = 9
    }
}