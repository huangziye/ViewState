package com.hzy.state.config

import android.util.SparseIntArray
import com.hzy.state.R
import com.hzy.state.annotation.ViewState

/**
 * 配置
 * @author: ziye_huang
 * @date: 2019/3/22
 */
class ViewStateConfig {

    companion object {
        /**
         * 状态数量
         */
        const val VIEW_STATE_SIZE = 9

        val ID_ROUTER = SparseIntArray()

        init {
            ID_ROUTER.put(ViewState.LOADING, R.id.view_stub_loading)
            ID_ROUTER.put(ViewState.NETWORK_ERROR, R.id.view_stub_network_error)
            ID_ROUTER.put(ViewState.LOAD_ERROR, R.id.view_stub_load_error)
            ID_ROUTER.put(ViewState.EMPTY, R.id.view_stub_empty)
            ID_ROUTER.put(ViewState.NOT_FOUND, R.id.view_stub_not_found)
            ID_ROUTER.put(ViewState.WIDGET_NETWORK_ERROR, R.id.view_stub_widget_network_error)
            ID_ROUTER.put(ViewState.WIDGET_ELFIN, R.id.view_stub_widget_hint)
            ID_ROUTER.put(ViewState.WIDGET_FLOAT, R.id.view_stub_widget_bottom_float)
        }
    }
}