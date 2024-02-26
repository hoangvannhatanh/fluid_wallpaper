package com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.ext

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.window.OnBackInvokedDispatcher
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.core.os.BuildCompat
import androidx.core.os.BuildCompat.PrereleaseSdkCheck

fun Activity.onCheckActivityIsFinished(): Boolean {
    return if (this != null) {
        if (this.isFinishing) {
            true
        } else {
            getCurrentSdkVersion() >= 17 && this.isDestroyed
        }
    } else {
        // == null -> die
        true
    }
}

fun ComponentActivity.onBackActivity() {
    @PrereleaseSdkCheck
    if (BuildCompat.isAtLeastT()) {
        onBackInvokedDispatcher.registerOnBackInvokedCallback(OnBackInvokedDispatcher.PRIORITY_DEFAULT) {
            finish()
        }
    } else {
        this.onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    finish()
                }
            })
    }
}

fun isNetwork(activity: Activity): Boolean {
    val cm = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
}