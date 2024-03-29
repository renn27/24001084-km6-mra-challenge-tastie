package com.refood.tastie

import android.app.Application
import com.refood.tastie.data.source.local.database.AppDatabase


class App : Application(){
    override fun onCreate() {
        super.onCreate()
        AppDatabase.getInstance(this)
    }
}