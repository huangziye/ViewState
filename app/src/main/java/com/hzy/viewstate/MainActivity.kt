package com.hzy.viewstate

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.hzy.state.annotation.ViewState
import com.hzy.state.controller.ViewStateController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var viewStateController: ViewStateController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.title = "ViewState 示例"

        btn_example_activity.setOnClickListener(this)
        btn_example_fragment.setOnClickListener(this)
        btn_example_view.setOnClickListener(this)
        btn_example_network_error_widget.setOnClickListener(this)
        btn_example_elfin.setOnClickListener(this)
        btn_example_elfin_cancel.setOnClickListener(this)
        btn_example_widget_bottom_float.setOnClickListener(this)

        viewStateController = ViewStateController.getInstance().bind(this)
        viewStateController.changeViewStateIgnore(ViewState.CONTENT)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_example_activity ->
                startActivity(Intent(this, ExampleActivity::class.java))
            R.id.btn_example_fragment -> startActivity(Intent(this, ExampleFragmentActivity::class.java))
            R.id.btn_example_view -> startActivity(Intent(this, ViewExampleActivity::class.java))
            R.id.btn_example_network_error_widget ->
                if (viewStateController.isVisibleViewState(ViewState.WIDGET_NETWORK_ERROR)) {
                    viewStateController.hideWidget(ViewState.WIDGET_NETWORK_ERROR)
                } else {
                    viewStateController.showWidget(ViewState.WIDGET_NETWORK_ERROR)
                }
            R.id.btn_example_elfin ->
                if (viewStateController.isVisibleViewState(ViewState.WIDGET_ELFIN)) {
                    viewStateController.hideWidget(ViewState.WIDGET_ELFIN)
                } else {
                    viewStateController.showWidget(ViewState.WIDGET_ELFIN)
                }
            R.id.btn_example_elfin_cancel ->
                if (viewStateController.isVisibleViewState(ViewState.WIDGET_ELFIN)) {
                    viewStateController.hideWidget(ViewState.WIDGET_ELFIN)
                } else {
                    viewStateController.showWidget(ViewState.WIDGET_ELFIN, 2000)
                }
            R.id.btn_example_widget_bottom_float ->
                if (viewStateController.isVisibleViewState(ViewState.WIDGET_FLOAT)) {
                    viewStateController.hideWidget(ViewState.LOADING)
                } else {
                    viewStateController.showWidget(ViewState.WIDGET_FLOAT, 2000)
                }
        }
    }
}
