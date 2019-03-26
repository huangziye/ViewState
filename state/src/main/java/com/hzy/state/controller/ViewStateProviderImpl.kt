package com.hzy.state.controller

import android.util.SparseArray
import androidx.annotation.IntRange
import androidx.annotation.NonNull
import com.hzy.state.annotation.ViewState
import com.hzy.state.bean.ViewStateData
import com.hzy.state.config.ViewStateConfig
import com.hzy.state.listener.OnLayoutChangeStateListener
import com.hzy.state.listener.OnRetryListener

/**
 * ViewState 视图内容相关提供者实现类
 * @author: ziye_huang
 * @date: 2019/3/22
 */
open class ViewStateProviderImpl<T> : IViewStateProvider<T> {

    /**
     * 状态视图数据集
     */
    private var mViewStateDataArray: SparseArray<ViewStateData> = SparseArray(ViewStateConfig.VIEW_STATE_SIZE)

    /**
     * 视图状态改变监听器
     */
    private var mOnLayoutChangeStateListener: OnLayoutChangeStateListener? = null

    /**
     * 重试时是否自动切换到加载中视图:true自动切换,false不切换
     */
    private var mIsAutoLoadingWhenRetry = true

    /**
     * 是否自动隐藏Elfin视图:true自动隐藏，false不隐藏.默认自动隐藏
     */
    private var mIsAutoHideElfin = true

    /**
     * Elfin显示时长.
     * 仅isAutoHideElfin = true;时有效.
     */
    private var mElfinDuration = 3000

    private var mNetworkErrorWidgetEnable = false

    /**
     * @param viewState 视图状态
     * @param layoutResId 开发者自定义的该状态视图
     */
    override fun addViewStateConfig(@ViewState viewState: Int, layoutResId: Int): T {
        getViewStateDataByViewState(viewState).layoutResId = layoutResId
        return this as T
    }

    /**
     *
     * @param viewState 视图状态
     * @param layoutResId 开发者自定义的该状态视图
     * @param retryTriggerViewId 该状态视图上点击事件的触发控件id
     * @param retryListener 点击事件触发后的回调
     */
    override fun addViewStateConfig(
        @ViewState
        viewState: Int,
        layoutResId: Int,
        retryTriggerViewId: Int,
        retryListener: OnRetryListener
    ): T {
        val viewStateData = getViewStateDataByViewState(viewState)
        viewStateData.layoutResId = layoutResId
        viewStateData.retryTriggerViewId = retryTriggerViewId
        viewStateData.retryListener = retryListener
        return this as T
    }

    override fun getViewStateConfig(@ViewState viewState: Int): ViewStateData {
        return getViewStateDataByViewState(viewState)
    }

    /**
     * 该方法只针对Widget相关状态有效
     * @param viewState 视图状态
     * @param marginTopPx widget相对于内容视图的顶部距离,比如可能需要预留出顶部Toolbar高度的距离
     * @param marginBottomPx widget相对于内容视图的底部距离,比如可能需要预留出底部导航栏高度的距离
     */
    override fun setWidgetMargin(
        @ViewState @IntRange(from = 7, to = 9) viewState: Int, marginTopPx: Int,
        marginBottomPx: Int
    ): T {
        val viewStateData = getViewStateDataByViewState(viewState)
        viewStateData.marginTopPx = marginTopPx
        viewStateData.marginBottomPx = marginBottomPx
        return this as T
    }

    override fun setRetryListener(@ViewState viewState: Int, retryListener: OnRetryListener): T {
        getViewStateDataByViewState(viewState).retryListener = retryListener
        return this as T
    }

    override fun setOnLayoutStateChangeListener(layoutChangeStateListener: OnLayoutChangeStateListener): T {
        mOnLayoutChangeStateListener = layoutChangeStateListener
        return this as T
    }

    override fun getOnLayoutStateChangeListener(): OnLayoutChangeStateListener? {
        return mOnLayoutChangeStateListener
    }

    override fun setAutoLoadingWithRetry(autoLoadingWithRetry: Boolean): T {
        mIsAutoLoadingWhenRetry = autoLoadingWithRetry
        return this as T
    }

    override fun isAutoLoadingWithRetry(): Boolean {
        return mIsAutoLoadingWhenRetry
    }

    @NonNull
    fun getViewStateDataByViewState(@ViewState viewState: Int): ViewStateData {
        var viewStateData = mViewStateDataArray.get(viewState)
        if (null == viewStateData) {
            viewStateData = ViewStateData(viewState)
            mViewStateDataArray.put(viewState, viewStateData)
        }
        return viewStateData
    }

    fun copyConfig(provider: ViewStateProviderImpl<T>) {
        try {
            for (index in 0 until mViewStateDataArray.size()) {
                provider.mViewStateDataArray.put(
                    mViewStateDataArray.keyAt(index),
                    mViewStateDataArray.valueAt(index).clone() as ViewStateData
                )
            }
            provider.mOnLayoutChangeStateListener = mOnLayoutChangeStateListener
            provider.mIsAutoLoadingWhenRetry = mIsAutoLoadingWhenRetry
            provider.mIsAutoHideElfin = mIsAutoHideElfin
            provider.mElfinDuration = mElfinDuration
            provider.mNetworkErrorWidgetEnable = mNetworkErrorWidgetEnable
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}