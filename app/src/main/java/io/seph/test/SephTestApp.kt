package io.seph.test

import android.app.Application

class SephTestApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        TODO("Not yet implemented")
    }
}