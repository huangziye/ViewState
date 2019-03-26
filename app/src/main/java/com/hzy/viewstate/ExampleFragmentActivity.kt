package com.hzy.viewstate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_example_fragment.*

/**
 *
 * @author: ziye_huang
 * @date: 2019/3/25
 */
class ExampleFragmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example_fragment)

        toolbar.title = "Fragment 示例"

        supportFragmentManager.beginTransaction().replace(R.id.fl_container, ExampleFragment()).commit()
    }
}