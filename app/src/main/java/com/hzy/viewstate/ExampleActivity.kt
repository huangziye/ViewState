package com.hzy.viewstate

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.hzy.state.annotation.ViewState
import com.hzy.state.controller.ViewStateController
import kotlinx.android.synthetic.main.activity_example.*

/**
 *
 * @author: ziye_huang
 * @date: 2019/3/25
 */
class ExampleActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mViewStateController: ViewStateController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)

        toolbar.title = "在Activity中使用"

        mViewStateController = ViewStateController.getInstance().bind(this)
        button.setOnClickListener(this)
        Handler().postDelayed({ mViewStateController.changeViewStateIgnore(ViewState.NETWORK_ERROR) }, 1000)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button -> {
                if (mViewStateController.isVisibleViewState(ViewState.WIDGET_ELFIN)) {
                    mViewStateController.hideWidget(ViewState.WIDGET_ELFIN)
                } else {
                    mViewStateController.showWidget(ViewState.WIDGET_ELFIN)
                }
            }
        }
    }
}