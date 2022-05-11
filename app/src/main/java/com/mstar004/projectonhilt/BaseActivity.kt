package com.mstar004.projectonhilt

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.kodein.di.KodeinAware

abstract class BaseActivity(private val layoutID: Int): AppCompatActivity(), KodeinAware {

    override val kodein by kodein()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setTheme(R.style.AppTheme)
        setContentView(layoutID)
        initView(savedInstanceState)
    }

    protected abstract fun initView(savedInstanceState: Bundle?)

    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
       // overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
       // overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
    }


}