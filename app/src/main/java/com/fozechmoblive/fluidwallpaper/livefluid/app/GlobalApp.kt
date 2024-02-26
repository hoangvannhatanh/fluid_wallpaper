package com.fozechmoblive.fluidwallpaper.livefluid.app

import android.annotation.SuppressLint
import android.app.Application
import com.fozechmoblive.fluidwallpaper.livefluid.BuildConfig
import com.fozechmoblive.fluidwallpaper.livefluid.utils.SharePrefUtils
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.*
import timber.log.Timber
import java.util.*

@HiltAndroidApp
class GlobalApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        SharePrefUtils.init(this)
    }
}