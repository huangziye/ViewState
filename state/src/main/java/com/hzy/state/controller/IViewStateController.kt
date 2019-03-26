package com.hzy.state.controller

import androidx.annotation.IntRange
import com.hzy.state.annotation.ViewState

/**
 * 状态控制器方法定义
 * @author: ziye_huang
 * @date: 2019/3/22
 */
interface IViewStateController {

    fun changeViewState(@ViewState @IntRange(from = 1, to = 6) viewState: Int)

    fun changeViewStateIgnore(@ViewState @IntRange(from = 1, to = 6) viewState: Int)

    @ViewState
    @IntRange(from = 1, to = 6)
    fun getCurrentViewState(): Int

    fun showWidget(@ViewState @IntRange(from = 7, to = 9) viewState: Int)

    fun showWidget(@ViewState @IntRange(from = 7, to = 9) viewState: Int, duration: Int)

    fun hideWidget(@ViewState @IntRange(from = 7, to = 9) viewState: Int)

    fun isVisibleViewState(@ViewState viewState: Int): Boolean
}
