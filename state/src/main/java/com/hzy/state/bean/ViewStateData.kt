package com.hzy.state.bean

import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import com.hzy.state.annotation.ViewState
import com.hzy.state.listener.OnRetryListener

/**
 * 状态视图数据.
 * @author: ziye_huang
 * @date: 2019/3/22
 */
class ViewStateData(
    @ViewState var viewState: Int, @LayoutRes var layoutResId: Int? = 0,
    @IdRes var retryTriggerViewId: Int? = 0, var retryListener: OnRetryListener? = null
) : Cloneable {

    /**
     * 仅 Widget 有效，一般为 ?attr/actionBarSize
     */
    var marginTopPx: Int = 0

    /**
     * 仅 Widget 有效
     */
    var marginBottomPx: Int = 0

    public override fun clone(): Any {
        return super.clone()
    }

}