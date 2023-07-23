package com.shagalalab.sozlik.android

import android.app.Application
import com.shagalalab.sozlik.shared.data.datastore.appContext

class SozlikApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        appContext = this
    }
}