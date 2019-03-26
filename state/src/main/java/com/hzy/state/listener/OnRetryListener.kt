package com.hzy.state.listener

import android.view.View
import com.hzy.state.controller.IViewStateController

/**
 * 重试监听器
 * @author: ziye_huang
 * @date: 2019/3/22
 */
interface OnRetryListener {

    /**
     * @param target bind Object
     * @param controller 当前视图状态控制器
     * @param trigger 重试触发控件
     */
    fun onViewStateRetry(target: Any, controller: IViewStateController, trigger: View)

}