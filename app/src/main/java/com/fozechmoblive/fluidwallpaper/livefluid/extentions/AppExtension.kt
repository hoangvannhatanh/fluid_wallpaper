package com.fozechmoblive.fluidwallpaper.livefluid.extentions

import android.app.Activity
import android.app.ActivityManager
import android.app.ActivityOptions
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.FontRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.fozechmoblive.fluidwallpaper.livefluid.R
import com.google.firebase.analytics.FirebaseAnalytics
import java.text.DecimalFormat

fun String?.formatStringNumber(format: String): String {
    return try {
        this?.run {
            DecimalFormat(format).format(this.toInt())
        } ?: ""
    } catch (ex: NumberFormatException) {
        this ?: ""
    }
}

fun TextView.setColorText(color: Int) {
    this.setTextColor(ContextCompat.getColor(context, color))
}

fun TextView.setStyleText(@FontRes font: Int) {
    this.typeface = ResourcesCompat.getFont(context, font)
}

fun TextView.setTextColorGradient(colorGradient1: String, colorGradient2: String) {
    val paint = this.paint
    val width = paint.measureText(this.text.toString())
    val textShader: Shader = LinearGradient(
        0f, 0f, width, this.textSize, intArrayOf(
            Color.parseColor(colorGradient1),
            Color.parseColor(colorGradient2)
        ), null, Shader.TileMode.REPEAT
    )
    this.paint.shader = textShader
}

fun View.setBackGroundDrawable(drawableRes: Int) {
    this.background = ContextCompat.getDrawable(context, drawableRes)
}

fun showActivity(context: Context, activity: Class<*>, bundle: Bundle? = null) {
    val intent = Intent(context, activity)
    intent.putExtras(bundle ?: Bundle())
    context.startActivity(intent)
}

fun showActivityAnimation(context: Activity, nameActivity: String, bundle: Bundle? = null) {
    val intent = Intent()
    intent.component = ComponentName(context, nameActivity)
    intent.putExtras(bundle ?: Bundle())
    context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(context).toBundle())
}

fun Class<*>.isMyServiceRunning(context: Context): Boolean {
    val manager = context.getSystemService(AppCompatActivity.ACTIVITY_SERVICE) as ActivityManager
    for (service in manager.getRunningServices(Int.MAX_VALUE)) {
        if (this.name == service.service.className) {
            return true
        }
    }
    return false
}

fun View.hide() {
    try {
        this.run {
            this.visibility = View.GONE
        }
    } catch (e: java.lang.Exception) {
        this.run {
            this.visibility = View.GONE
        }
    }
}

fun View.show() {
    try {
        this.run {
            this.visibility = View.VISIBLE
        }
    } catch (e: java.lang.Exception) {
        this.run {
            this.visibility = View.GONE
        }
    }
}

fun setPref(c: Context, pref: String, value: String) {
    val e = PreferenceManager.getDefaultSharedPreferences(c).edit()
    e.putString(pref, value)
    e.apply()

}

fun getPref(c: Context, pref: String, value: String): String? {
    return PreferenceManager.getDefaultSharedPreferences(c).getString(
        pref,
        value
    )
}

fun setPref(c: Context, pref: String, value: Boolean) {
    val e = PreferenceManager.getDefaultSharedPreferences(c).edit()
    e.putBoolean(pref, value)
    e.apply()

}

fun getPref(c: Context, pref: String, value: Boolean): Boolean {
    return PreferenceManager.getDefaultSharedPreferences(c).getBoolean(
        pref, value
    )
}

fun setPref(c: Context, pref: String, value: Int) {
    val e = PreferenceManager.getDefaultSharedPreferences(c).edit()
    e.putInt(pref, value)
    e.apply()

}

fun getPref(c: Context, pref: String, value: Int): Int {
    return PreferenceManager.getDefaultSharedPreferences(c).getInt(
        pref, value
    )
}


fun <T : View> T.onClickDelay(block: T.() -> Unit) {

    onClick(200, block)
}

private var lastClick = 0L
fun <T : View> T.onClick(delayBetweenClick: Long = 0, block: T.() -> Unit) {
    setOnClickListener {
        when {
            delayBetweenClick <= 0 -> {
                block()
            }

            System.currentTimeMillis() - lastClick > delayBetweenClick -> {
                lastClick = System.currentTimeMillis()
                block()
            }

            else -> {

            }
        }
    }
}

fun Activity.hideKeyboard() {
    val inputMethodManager =
        this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)
}

fun Activity.showKeyboard(editText: View) {
    val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
}

fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val nw = connectivityManager.activeNetwork ?: return false
    val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
    return when {
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        //for other device how are able to connect with Ethernet
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        //for check internet over Bluetooth
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
        else -> false
    }
}

fun isQPlus() = Build.VERSION.SDK_INT > Build.VERSION_CODES.Q

fun is32Minus() = Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2

fun is32Plus() = Build.VERSION.SDK_INT > Build.VERSION_CODES.S_V2

//fun showCustomToast(message: String, activity: Activity) {
//        val toast = Toast(activity)
//        val layout = activity.layoutInflater.inflate (R.layout.layout_custom_toast, activity.findViewById(R.id.toast_container))
//        if (message.isNotEmpty()){
//            val textView = layout.findViewById<TextView>(R.id.toast_text)
//            textView.text = message
//     }
//        toast.apply {
//            setGravity(Gravity.BOTTOM, 0, 50)
//            duration = Toast.LENGTH_SHORT
//            view = layout
//            show()
//    }
//}

fun firebaseAnalyticsEvent(context: Context, key: String, data: String) {
     if (isNetworkAvailable(context)) {
         val bundle = Bundle()
         bundle.putString(key, data)
         val analytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(context)
         analytics.logEvent(key, bundle)
     }
}

fun isCameraAvailable(context: Context): Boolean {
    val packageManager = context.packageManager
    return packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)
}