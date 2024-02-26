package com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.ext

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast

internal const val CHECK_TIME_MULTI_CLICK = 500
private var mLastClickTime: Long = 0


fun Context.showKeyboard(focusEditText: EditText) {
    val pos: Int = focusEditText.text.length
    focusEditText.setSelection(pos)
    Handler(Looper.getMainLooper()).postDelayed({
        focusEditText.requestFocus()
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(focusEditText, InputMethodManager.SHOW_IMPLICIT)
    }, 500)
}

fun Context.canTouch(): Boolean {
    if (SystemClock.elapsedRealtime() - mLastClickTime < CHECK_TIME_MULTI_CLICK) {
        return false
    }
    mLastClickTime = SystemClock.elapsedRealtime()
    return true
}

fun Context.showToastByString(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showToastById(id: Int) {
    Toast.makeText(this, resources.getString(id), Toast.LENGTH_SHORT).show()
}

fun Context.getStringById(id: Int): String {
    return resources.getString(id)
}

fun Context.getCurrentSdkVersion(): Int {
    return Build.VERSION.SDK_INT
}

fun Context.isNetwork(): Boolean {
    val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return cm.activeNetworkInfo != null && cm.activeNetworkInfo?.isConnected == true
}