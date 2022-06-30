package kr.ayaan.presentation

import android.app.Application
import android.content.Context

class MyApplication: Application() {
    init {
        instance = this
    }
    companion object {
        lateinit var instance: MyApplication
        fun getApplicationContext(): Context = instance.applicationContext
    }
}