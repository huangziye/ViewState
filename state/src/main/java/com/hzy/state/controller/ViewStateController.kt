package com.hzy.state.controller

import android.view.View
import androidx.annotation.IntRange
import com.hzy.state.annotation.ViewState
import com.hzy.state.bean.ViewStateData
import com.hzy.state.listener.OnLayoutChangeStateListener
import com.hzy.state.listener.OnRetryListener
import com.hzy.state.util.BindUtil
import com.hzy.state.widget.ViewStateLayout

/**
 * 视图状态控制器
 * @author: ziye_huang
 * @date: 2019/3/22
 */
class ViewStateController : IViewStateProvider<ViewStateController>, IViewStateController {

    private var mViewStateProvider: IViewStateProvider<ViewStateManager> = ViewStateProviderImpl()
    private lateinit var mViewStateLayout: ViewStateLayout

    private constructor() {
        ViewStateManager.getInstance().copyConfig(mViewStateProvider as ViewStateProviderImpl<ViewStateManager>)
    }

    companion object {
        fun getInstance(): ViewStateController {
            return ViewStateController()
        }
    }

    fun bind(target: Any): ViewStateController {
        mViewStateLayout = BindUtil.bind(target)
        mViewStateLayout.setViewStateController(this)
        //默认显示加载中视图
        changeViewState(ViewState.LOADING)
        return this
    }

    fun bindFragment(fragmentView: View): View {
        mViewStateLayout = BindUtil.bindFragmentView(fragmentView)
        mViewStateLayout.setViewStateController(this)
        //默认显示加载中视图
        changeViewState(ViewState.LOADING)
        return mViewStateLayout
    }

    override fun changeViewState(@ViewState viewState: Int) {
        mViewStateLayout.changeViewState(viewState)
    }

    override fun changeViewStateIgnore(@ViewState viewState: Int) {
        mViewStateLayout.changeViewStateIgnore(viewState)
    }

    override fun getCurrentViewState(): Int {
        return mViewStateLayout.getCurrentViewState()
    }

    override fun showWidget(@ViewState @IntRange(from = 7, to = 9) viewState: Int) {
        mViewStateLayout.showWidget(viewState)
    }

    override fun showWidget(@ViewState @IntRange(from = 7, to = 9) viewState: Int, duration: Int) {
        mViewStateLayout.showWidget(viewState, duration)
    }

    override fun hideWidget(@ViewState @IntRange(from = 7, to = 9) viewState: Int) {
        mViewStateLayout.hideWidget(viewState)
    }

    override fun isVisibleViewState(@ViewState viewState: Int): Boolean {
        return mViewStateLayout.isVisibleViewState(viewState)
    }

    //-----------------------------------

    override fun addViewStateConfig(viewState: Int, layoutResId: Int): ViewStateController {
        mViewStateProvider.addViewStateConfig(viewState, layoutResId)
        return this
    }

    override fun addViewStateConfig(
        viewState: Int,
        layoutResId: Int,
        retryTriggerViewId: Int,
        retryListener: OnRetryListener
    ): ViewStateController {
        mViewStateProvider.addViewStateConfig(viewState, layoutResId, retryTriggerViewId, retryListener)
        return this
    }

    override fun getViewStateConfig(viewState: Int): ViewStateData? {
        return mViewStateProvider.getViewStateConfig(viewState)
    }

    override fun setWidgetMargin(
        @ViewState @IntRange(from = 7, to = 9) viewState: Int, marginTopPx: Int,
        marginBottomPx: Int
    ): ViewStateController {
        mViewStateProvider.setWidgetMargin(viewState, marginTopPx, marginBottomPx)
        return this
    }

    override fun setRetryListener(viewState: Int, retryListener: OnRetryListener): ViewStateController {
        mViewStateProvider.setRetryListener(viewState, retryListener)
        return this
    }

    override fun setOnLayoutStateChangeListener(layoutChangeStateListener: OnLayoutChangeStateListener): ViewStateController {
        mViewStateProvider.setOnLayoutStateChangeListener(layoutChangeStateListener)
        return this
    }

    override fun getOnLayoutStateChangeListener(): OnLayoutChangeStateListener? {
        return mViewStateProvider.getOnLayoutStateChangeListener()
    }

    override fun setAutoLoadingWithRetry(autoLoadingWithRetry: Boolean): ViewStateController {
        mViewStateProvider.setAutoLoadingWithRetry(autoLoadingWithRetry)
        return this
    }

    override fun isAutoLoadingWithRetry(): Boolean {
        return mViewStateProvider.isAutoLoadingWithRetry()
    }
}