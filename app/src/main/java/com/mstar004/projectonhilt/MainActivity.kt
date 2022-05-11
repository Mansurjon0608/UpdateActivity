package com.mstar004.projectonhilt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.android.BuildConfig

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
    }

    private fun checkVersion() {
        GlobalScope.launch(Dispatchers.IO) { //You must add implementation of Coroutines
            if (unitProvider.isOnline()) { //interface checking for network provider
                try {
                    val response = apiService.getVersions() //here is your API where you are checking Version in String type on response.successful
                    if (response.isSuccessful) {
                        if (response.body()!! != BuildConfig.VERSION_NAME) { //VERSION_NAME = "1.0.0" right now
                            startActivity(Intent(this@MainActivity, UpdateActivity::class.java))
                            finish()
                        }
                    }
                } catch (e: java.lang.Exception) {
                    Log.e("BAG", "checkVersion: $e")
                }
            }
        }
    }
}