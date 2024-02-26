package com.fozechmoblive.fluidwallpaper.livefluid.ui.bases

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.fozechmoblive.fluidwallpaper.livefluid.R


abstract class BaseBottomSheetDialog<VB : ViewDataBinding>(
    context: Context,
    themeResId: Int = R.style.ThemeBottomSheetDialog
) :
    BottomSheetDialog(context, themeResId) {
    lateinit var mBinding: VB

    init {
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
        if (!::mBinding.isInitialized) {
            mBinding =
                DataBindingUtil.inflate(LayoutInflater.from(context), layoutView, null, false)
        }
        setContentView(mBinding.root)
    }

    abstract fun getLayoutDialog(): Int

    open fun initViews() {}

    open fun onResizeViews() {}

    open fun onClickViews() {}
}