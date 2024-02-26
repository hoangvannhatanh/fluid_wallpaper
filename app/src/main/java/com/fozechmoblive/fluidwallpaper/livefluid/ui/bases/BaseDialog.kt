package com.fozechmoblive.fluidwallpaper.livefluid.ui.bases

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.fozechmoblive.fluidwallpaper.livefluid.R


abstract class BaseDialog<VB : ViewDataBinding>(
    context: Context,
    themeResId: Int = R.style.ThemeDialog
) :
    Dialog(context, themeResId) {
    lateinit var mBinding: VB

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        createContentView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        onResizeViews()
        onClickViews()
    }

    private fun createContentView() {
        val layoutView = getLayoutDialog()
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(context), layoutView, null, false)
        setContentView(mBinding.root)
    }

    abstract fun getLayoutDialog(): Int

    open fun initViews() {}

    open fun onResizeViews() {}

    open fun onClickViews() {}


    fun setDialogBottom() {
        window?.run {
            setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            setGravity(Gravity.BOTTOM)
        }
    }
}