package com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.ext

import android.app.Activity
import android.os.SystemClock
import android.view.View
import android.view.WindowManager
import androidx.databinding.ViewDataBinding
import com.fozechmoblive.fluidwallpaper.livefluid.app.AppConstants
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.dialog.DialogRateApp
import com.fozechmoblive.fluidwallpaper.livefluid.utils.SharePrefUtils
import com.fozechmoblive.fluidwallpaper.livefluid.utils.SharePrefUtils.forceRated
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.tasks.Task

internal const val DISPLAY = 1080

fun View.goneView() {
    visibility = View.GONE
}

fun View.visibleView() {
    visibility = View.VISIBLE
}

fun View.invisibleView() {
    visibility = View.INVISIBLE
}

fun View.isVisible() = visibility == View.VISIBLE

fun View.isInvisible() = visibility == View.INVISIBLE

fun View.isGone() = visibility == View.GONE

fun ViewDataBinding.goneView() {
    this.root.goneView()
}

fun ViewDataBinding.visibleView() {
    this.root.visibleView()
}

fun ViewDataBinding.invisibleView() {
    this.root.invisibleView()
}

fun ViewDataBinding.isVisible() = this.root.visibility == View.VISIBLE

fun ViewDataBinding.isInvisible() = this.root.visibility == View.INVISIBLE

fun ViewDataBinding.isGone() = this.root.visibility == View.GONE

/*Resize View*/
fun View.resizeView(width: Int, height: Int = 0) {
    val pW = context.getWidthScreenPx() * width / DISPLAY
    val pH = if (height == 0) pW else pW * height / width
    val params = layoutParams
    params.let {
        it.width = pW
        it.height = pH
    }
}


fun View.click(action: (view: View?) -> Unit) {     setOnClickListener(object : TapListener() {         override fun onTap(v: View?) {             action(v)         }     }) }
abstract class TapListener : View.OnClickListener {
    companion object {
        private const val DEBOUNCE = 300L
    }

    private var lastClickMillis: Long = 0
    private var now = 0L

    override fun onClick(v: View?) {
        now = SystemClock.elapsedRealtime()
        if (now - lastClickMillis > DEBOUNCE)
            onTap(v)
        lastClickMillis = now
    }

    abstract fun onTap(v: View?)
}

fun showRateDialog(
    activity: Activity, isFinish: Boolean
) {
    val dialog = DialogRateApp(activity, onRating = {
        SharePrefUtils.putBoolean(AppConstants.IS_RATED, true)
        val manager: ReviewManager = ReviewManagerFactory.create(activity)
        val request: Task<ReviewInfo> = manager.requestReviewFlow()
        request.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val reviewInfo: ReviewInfo = task.result
                val flow: Task<Void> = manager.launchReviewFlow(
                    activity, reviewInfo
                )
                flow.addOnSuccessListener {
                    forceRated(activity)
                    if (isFinish) {
                        activity.finishAffinity()
                        activity.finish()
                    }
                }
            } else {
                if (isFinish) {
                    activity.finishAffinity()
                    activity.finish()
                }
            }
        }
    })
    try {
        dialog.show()
    } catch (e: WindowManager.BadTokenException) {
        e.printStackTrace()
    }
}