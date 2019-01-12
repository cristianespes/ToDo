package com.cristianespes.todo.ui

import android.os.Bundle
import com.cristianespes.todo.R
import com.cristianespes.todo.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}
