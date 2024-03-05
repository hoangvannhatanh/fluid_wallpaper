package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.dialog

import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.CountDownTimer
import android.text.TextPaint
import android.util.Log
import androidx.core.content.ContextCompat
import com.fozechmoblive.fluidwallpaper.livefluid.R
import com.fozechmoblive.fluidwallpaper.livefluid.databinding.DialogLoadingPresetBinding
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.BaseDialog
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.WallpaperLiveViewActivity

class DialogLoading(val context: WallpaperLiveViewActivity, val onFinishedLoading: () -> Unit) :
    BaseDialog<DialogLoadingPresetBinding>(context) {
    override fun getLayoutDialog(): Int = R.layout.dialog_loading_preset

    override fun initViews() {
        super.initViews()
        setCancelable(false)
        val timeCountDown = 2500L
        var x = 0

        object : CountDownTimer(timeCountDown, 100) {
            override fun onTick(millisUntilFinished: Long) {
                x += 7
                mBinding.pbLoading.progress = x
                Log.d("Ynsuper", "onTick: $x  -- $millisUntilFinished")
            }

            override fun onFinish() {
                onFinishedLoading.invoke()
                dismiss()
            }

        }.start()

        val paint: TextPaint = mBinding.tvLoading.paint
        val width: Float = paint.measureText(context.getString(R.string.txt_loading))

        val textShader: Shader = LinearGradient(
            0f, 0f, width, mBinding.tvLoading.paint.textSize, intArrayOf(
                ContextCompat.getColor(context, R.color.color_0E1822),
                ContextCompat.getColor(context, R.color.color_686C70)
            ), null, Shader.TileMode.CLAMP
        )
        mBinding.tvLoading.paint.shader = textShader
    }

}