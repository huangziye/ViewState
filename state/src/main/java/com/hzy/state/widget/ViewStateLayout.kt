package com.hzy.state.widget

import android.animation.LayoutTransition
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewStub
import android.widget.FrameLayout
import com.hzy.state.R
import com.hzy.state.annotation.ViewState
import com.hzy.state.config.ViewStateConfig
import com.hzy.state.controller.IViewStateController
import com.hzy.state.controller.ViewStateController
import com.hzy.state.controller.ViewStateNetworkStateProvider


/**
 * 状态布局控件
 * @author: ziye_huang
 * @date: 2019/3/25
 */
class ViewStateLayout : FrameLayout, IViewStateController, View.OnClickListener {
    private var mViewStateViewCache: SparseArray<View> = SparseArray(ViewStateConfig.VIEW_STATE_SIZE)
    @ViewState
    private var mCurrentViewState = 0
    private var mViewStateController: ViewStateController? = null
    private var mTarget: Any
    private var mContentView: View

    constructor(contentView: View, target: Any) : super(contentView.context) {
        mContentView = contentView
        mTarget = target
        LayoutInflater.from(contentView.context).inflate(R.layout.view_state_controller_layout, this)
        mContentView.visibility = View.GONE
        cacheView(ViewState.CONTENT, mContentView)
        super.addView(mContentView, 0)
        layoutTransition = LayoutTransition()
    }

    /**
     * 缓存View
     */
    private fun cacheView(@ViewState viewState: Int, view: View) {
        mViewStateViewCache.put(viewState, view)
    }

    fun setViewStateController(controller: ViewStateController) {
        mViewStateController = controller
    }

    /**
     * 判断是否是小部件
     */
    fun isWidget(@ViewState viewState: Int): Boolean {
        return ViewState.WIDGET_NETWORK_ERROR == viewState || ViewState.WIDGET_ELFIN == viewState || ViewState.WIDGET_FLOAT == viewState
    }

    private fun getViewByViewState(@ViewState viewState: Int): View? {
        var temp = mViewStateViewCache.get(viewState)
        if (null == temp) temp = findViewByViewState(viewState)
        return temp
    }

    private fun findViewByViewState(@ViewState viewState: Int): View? {
        if (viewState == ViewState.CONTENT) {
            return mContentView
        }
        val viewStubId = ViewStateConfig.ID_ROUTER[viewState]
        var viewStateData = mViewStateController?.getViewStateConfig(viewState)
        if (null == viewStateData || viewStateData.layoutResId!! <= 0) return null
        val viewStub = findViewById<ViewStub>(viewStubId)
        viewStub.layoutResource = viewStateData.layoutResId!!
        val view = viewStub.inflate()

        if (isWidget(viewState)) {
            if (viewStateData.marginTopPx > 0) {
                (view.layoutParams as MarginLayoutParams).topMargin = viewStateData.marginTopPx
            }
            if (viewStateData.marginBottomPx > 0) {
                (view.layoutParams as MarginLayoutParams).bottomMargin = viewStateData.marginBottomPx
            }
        }

        val triggerView = view.findViewById<View>(viewStateData.retryTriggerViewId!!)
        if (null != triggerView) {
            triggerView.tag = viewState
            triggerView.setOnClickListener(this)
        }
        cacheView(viewState, view)
        return view
    }

    private fun handleVisibilityChange(@ViewState viewState: Int, visibility: Int) {
        setViewVisibility(viewState, getViewByViewState(viewState), visibility)
    }

    private fun setViewVisibility(@ViewState viewState: Int, view: View?, visibility: Int) {
        if (null == view) return
        val onLayoutStateChangeListener = mViewStateController?.getOnLayoutStateChangeListener()
        onLayoutStateChangeListener?.onPrepareChange(mTarget, view, viewState, visibility == View.VISIBLE)
        view.visibility = visibility
        onLayoutStateChangeListener?.onChangeComplete(mTarget, view, viewState, visibility == View.GONE)
    }

    private fun changeViewByState(@ViewState viewState: Int, ignore: Boolean) {
        //Widget重定向
        if (isWidget(viewState)) {
            if (isVisibleViewState(viewState)) {
                hideWidget(viewState)
            } else {
                showWidget(viewState)
            }
            return
        }

        //网络错误时重定向
        var tempViewState = viewState
        if (!ViewStateNetworkStateProvider.getInstance().isConnected(context)) {
            tempViewState = ViewState.NETWORK_ERROR
        }

        if (mCurrentViewState == tempViewState) {
            //nothing.
            return
        } else if (ignore && ViewState.CONTENT == mCurrentViewState) {
            //nothing.
            return
        }

        handleVisibilityChange(mCurrentViewState, View.GONE)
        mCurrentViewState = tempViewState
        handleVisibilityChange(mCurrentViewState, View.VISIBLE)
    }

    override fun changeViewState(@ViewState viewState: Int) {
        changeViewByState(viewState, false)
    }

    override fun changeViewStateIgnore(@ViewState viewState: Int) {
        changeViewByState(viewState, true)
    }

    @ViewState
    override fun getCurrentViewState(): Int {
        return mCurrentViewState
    }

    override fun showWidget(@ViewState viewState: Int) {
        if (isWidget(viewState)) {
            handleVisibilityChange(viewState, View.VISIBLE)
        }
    }

    override fun showWidget(@ViewState viewState: Int, duration: Int) {
        if (isWidget(viewState)) {
            handleVisibilityChange(viewState, View.VISIBLE)
            if (duration > 0) {
                postDelayed({ hideWidget(viewState) }, duration.toLong())
            }
        }
    }

    override fun hideWidget(@ViewState viewState: Int) {
        if (isWidget(viewState)) {
            handleVisibilityChange(viewState, View.GONE)
        }
    }

    override fun isVisibleViewState(@ViewState viewState: Int): Boolean {
        //只需要从缓存获取就好了,缓存都没有那肯定是没有显示过了
        var cacheView = mViewStateViewCache.get(viewState)
        return null != cacheView && cacheView.visibility == View.VISIBLE
    }

    override fun onClick(v: View?) {
        dispatchTrigger(v?.tag as Int, v)
    }

    private fun dispatchTrigger(@ViewState viewState: Int, trigger: View) {
        val viewStateData = mViewStateController?.getViewStateConfig(viewState)
        if (trigger.id != viewStateData?.retryTriggerViewId) return

        if (null != viewStateData.retryListener) {
            if (mViewStateController!!.isAutoLoadingWithRetry() && (ViewState.NETWORK_ERROR == viewState
                        || ViewState.LOAD_ERROR == viewState
                        || ViewState.EMPTY == viewState)
            ) {
                changeViewStateIgnore(ViewState.LOADING)
            }
            viewStateData.retryListener?.onViewStateRetry(mTarget, mViewStateController!!, trigger)
        }
    }
}