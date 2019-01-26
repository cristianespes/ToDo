package com.cristianespes.todo.ui.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.widget.Toolbar
import com.cristianespes.todo.R

abstract class BaseActivity : AppCompatActivity() {

    protected fun setUpToolbar(homeIsEnabled: Boolean) {
        val toolbar = findViewById<Toolbar>(R.id.toolbar).apply {
            setSubtitle(null)
        }

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(homeIsEnabled) // Back Button
    }

    // Back View
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
    }

}
