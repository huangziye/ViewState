package com.hzy.viewstate

import android.app.Application
import android.content.Context
import android.os.Handler
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import com.hzy.state.annotation.ViewState
import com.hzy.state.controller.IViewStateController
import com.hzy.state.controller.ViewStateManager
import com.hzy.state.controller.ViewStateNetworkStateProvider
import com.hzy.state.listener.OnRequestNetworkStateEvent
import com.hzy.state.listener.OnRetryListener


/**
 *
 * @author: ziye_huang
 * @date: 2019/3/25
 */
class App : Application() {

    private var actionBarHeight = 0
    override fun onCreate() {
        super.onCreate()
        // Calculate ActionBar height
        val tv = TypedValue()
        if (theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, resources.displayMetrics)
        }

        ViewStateManager.getInstance()
            .setWidgetMargin(ViewState.WIDGET_NETWORK_ERROR, actionBarHeight, 0)
            .setWidgetMargin(ViewState.WIDGET_ELFIN, actionBarHeight, 0)
            .setWidgetMargin(ViewState.WIDGET_FLOAT, 0, 0)
            .addViewStateConfig(ViewState.LOADING, R.layout.view_state_layout_loading)//加载中.
            .addViewStateConfig(
                ViewState.NETWORK_ERROR,
                R.layout.view_state_layout_network_error,
                R.id.tv_network_error_retry,
                object : OnRetryListener {
                    override fun onViewStateRetry(target: Any, controller: IViewStateController, trigger: View) {
                        Toast.makeText(trigger.context, "网络错误重试", Toast.LENGTH_LONG).show()
                        Handler().postDelayed({ controller.changeViewState(ViewState.LOAD_ERROR) }, 1000)
                    }
                })//网络错误.
            .addViewStateConfig(
                ViewState.LOAD_ERROR,
                R.layout.view_state_layout_loading_error,
                R.id.tv_load_error_retry,
                object : OnRetryListener {
                    override fun onViewStateRetry(target: Any, controller: IViewStateController, trigger: View) {
                        Toast.makeText(trigger.context, "加载失败重试", Toast.LENGTH_SHORT).show()
                        Handler().postDelayed({ controller.changeViewState(ViewState.EMPTY) }, 1000)
                    }
                })//加载失败.
            .addViewStateConfig(
                ViewState.EMPTY,
                R.layout.view_state_layout_empty,
                R.id.tv_empty_retry,
                object : OnRetryListener {
                    override fun onViewStateRetry(target: Any, controller: IViewStateController, trigger: View) {
                        Toast.makeText(trigger.context, "空布局重试", Toast.LENGTH_SHORT).show()
                        Handler().postDelayed({ controller.changeViewState(ViewState.NOT_FOUND) }, 1000)
                    }
                })//空布局.
            .addViewStateConfig(
                ViewState.NOT_FOUND,
                R.layout.view_state_layout_not_found,
                R.id.tv_not_found_retry,
                object : OnRetryListener {
                    override fun onViewStateRetry(target: Any, controller: IViewStateController, trigger: View) {
                        Toast.makeText(trigger.context, "未找到内容重试", Toast.LENGTH_SHORT).show()
                        Handler().postDelayed({
                            controller.changeViewState(ViewState.CONTENT)
                            Handler().postDelayed({ controller.changeViewState(ViewState.WIDGET_ELFIN) }, 1000)
                        }, 1000)
                    }
                })//未找到内容布局.
            .addViewStateConfig(
                ViewState.WIDGET_ELFIN,
                R.layout.view_state_layout_hint,
                R.id.tv_hint_retry,
                object : OnRetryListener {
                    override fun onViewStateRetry(target: Any, controller: IViewStateController, trigger: View) {
                        Toast.makeText(trigger.context, "提示内容重试", Toast.LENGTH_SHORT).show()
                        Handler().postDelayed({
                            Handler().postDelayed({
                                controller.changeViewState(
                                    ViewState.WIDGET_ELFIN
                                )
                            }, 1000)
                        }, 1000)
                    }
                })//提示布局.
            .addViewStateConfig(
                ViewState.WIDGET_NETWORK_ERROR,
                R.layout.view_state_widget_network_error,
                R.id.tv_check_network,
                object : OnRetryListener {
                    override fun onViewStateRetry(target: Any, controller: IViewStateController, trigger: View) {
                        Toast.makeText(trigger.context, "检查网络设置", Toast.LENGTH_SHORT).show()
                    }
                })
            .addViewStateConfig(
                ViewState.WIDGET_FLOAT,
                R.layout.view_state_widget_float,
                R.id.tv_float,
                object : OnRetryListener {
                    override fun onViewStateRetry(target: Any, controller: IViewStateController, trigger: View) {
                        Toast.makeText(trigger.context, "我是Float", Toast.LENGTH_SHORT).show()
                    }
                })

        ViewStateNetworkStateProvider.getInstance()
            .registerOnRequestNetworkStateEvent(object : OnRequestNetworkStateEvent {
                override fun onRequestNetworkState(context: Context): Boolean {
                    return NetworkManager.isConnected(context)
                }
            })
    }

}