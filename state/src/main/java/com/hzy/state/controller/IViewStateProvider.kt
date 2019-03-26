package com.hzy.state.controller

import androidx.annotation.IdRes
import androidx.annotation.IntRange
import androidx.annotation.LayoutRes
import com.hzy.state.annotation.ViewState
import com.hzy.state.bean.ViewStateData
import com.hzy.state.listener.OnLayoutChangeStateListener
import com.hzy.state.listener.OnRetryListener

/**
 * ViewState 视图内容相关提供者
 * @author: ziye_huang
 * @date: 2019/3/22
 */
interface IViewStateProvider<out T> {

    fun addViewStateConfig(@ViewState viewState: Int, @LayoutRes layoutResId: Int): T

    fun addViewStateConfig(
        @ViewState viewState: Int, @LayoutRes layoutResId: Int, @IdRes retryTriggerViewId: Int, retryListener: OnRetryListener
    ): T

    fun getViewStateConfig(@ViewState viewState: Int): ViewStateData?

    fun setWidgetMargin(@ViewState @IntRange(from = 7, to = 9) viewState: Int, marginTopPx: Int, marginBottomPx: Int): T

    fun setRetryListener(@ViewState viewState: Int, retryListener: OnRetryListener): T

    fun setOnLayoutStateChangeListener(layoutChangeStateListener: OnLayoutChangeStateListener): T

    fun getOnLayoutStateChangeListener(): OnLayoutChangeStateListener?

    fun setAutoLoadingWithRetry(autoLoadingWithRetry: Boolean): T

    fun isAutoLoadingWithRetry(): Boolean

}