package com.hzy.viewstate

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.hzy.state.annotation.ViewState
import com.hzy.state.controller.ViewStateController
import kotlinx.android.synthetic.main.view_example.*

/**
 *
 * @author: ziye_huang
 * @date: 2019/3/25
 */
class ViewExampleActivity : AppCompatActivity() {

    private lateinit var mViewStateController: ViewStateController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_example)

        toolbar.title = "View中使用示例"

        mViewStateController = ViewStateController.getInstance().bind(findViewById(R.id.tv_example))
    }

    override fun onResume() {
        super.onResume()
        Handler().postDelayed({
            mViewStateController.changeViewStateIgnore(ViewState.CONTENT)
        }, 1000)
    }
}