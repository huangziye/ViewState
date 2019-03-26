package com.hzy.state.listener

import android.view.View
import com.hzy.state.annotation.ViewState

/**
 * 状态改变监听器
 * @author: ziye_huang
 * @date: 2019/3/22
 */
interface OnLayoutChangeStateListener {

    /**
     * 准备改变
     * @param target bind Object
     * @param view 视图，注意：在非内容视图时第一次为null
     * @param viewState ViewStateConfig
     * @param isShow true显示,false隐藏
     */
    fun onPrepareChange(target: Any, view: View, @ViewState viewState: Int, isShow: Boolean)

    /**
     * 改变完成
     * @param target bind Object
     * @param view 视图
     * @param viewState ViewStateConfig
     * @param isShow true显示,false隐藏
     */
    fun onChangeComplete(target: Any, view: View, @ViewState viewState: Int, isShow: Boolean)

}