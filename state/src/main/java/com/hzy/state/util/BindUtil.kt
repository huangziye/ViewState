package com.hzy.state.util

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import com.hzy.state.widget.ViewStateLayout


/**
 * Bind帮助类
 * @author: ziye_huang
 * @date: 2019/3/25
 */
object BindUtil {

    /**
     * 绑定Activity
     */
    fun bind(target: Any): ViewStateLayout {
        val contentParent: ViewGroup
        var contentIndex = 0
        if (target is Activity) {
            contentParent = target.window.findViewById(android.R.id.content)
        } else if (target is View) {
            contentParent = target.parent as ViewGroup
            var child: View
            val childCount = contentParent.childCount
            for (i in 0 until childCount) {
                child = contentParent.getChildAt(i)
                if (child === target) {
                    contentIndex = i
                    break
                }
            }
        } else {
            throw IllegalArgumentException("Check your target is Activity or Fragment or View.")
        }

        return bindView(target, contentParent, contentIndex)
    }

    /**
     * 绑定fragment
     */
    fun bindFragmentView(fragmentView: View): ViewStateLayout {
        return createViewStateLayout(fragmentView, fragmentView)
    }

    /**
     * 绑定View
     */
    private fun bindView(target: Any, parentView: ViewGroup, index: Int): ViewStateLayout {
        val contentView = parentView.getChildAt(index)
        parentView.removeViewAt(index)
        val viewStateLayout = createViewStateLayout(contentView, target)
        parentView.addView(viewStateLayout, index, contentView.layoutParams)
        return viewStateLayout
    }

    private fun createViewStateLayout(contentView: View, target: Any): ViewStateLayout {
        return ViewStateLayout(contentView, target)
    }
}