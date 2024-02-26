package com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.ext

import android.content.Context
import android.util.DisplayMetrics

fun Number.dpToPx(context: Context): Float {
    return this.toFloat() * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

fun Number.pxToDp(context: Context): Float {
    return this.toFloat() / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

fun Context.getWidthScreenPx(): Int {
    return resources.displayMetrics.widthPixels
}

fun Context.getHeightScreenPx(): Int {
    return resources.displayMetrics.heightPixels
}

fun Context.getWidthScreenDp(): Float {
    return resources.displayMetrics.widthPixels.pxToDp(this)
}

fun Context.getHeightScreenDp(): Float {
    return resources.displayMetrics.widthPixels.dpToPx(this)
}

