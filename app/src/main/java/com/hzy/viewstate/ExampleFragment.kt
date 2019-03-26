package com.hzy.viewstate

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.hzy.state.annotation.ViewState
import com.hzy.state.controller.ViewStateController

/**
 *
 * @author: ziye_huang
 * @date: 2019/3/26
 */
class ExampleFragment : Fragment() {

    private lateinit var mViewStateController: ViewStateController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val textView = TextView(context)
        textView.layoutParams = ViewGroup.LayoutParams(-1, -1)
        textView.text = "我是Fragment"
        textView.setTextColor(Color.BLACK)
        textView.textSize = 48f
        textView.gravity = Gravity.CENTER
        mViewStateController = ViewStateController.getInstance()
        return mViewStateController.bindFragment(textView)
    }

    override fun onResume() {
        super.onResume()
        Handler().postDelayed({
            mViewStateController.changeViewStateIgnore(ViewState.CONTENT)
        }, 1000)
    }

}