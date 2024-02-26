package com.fozechmoblive.fluidwallpaper.livefluid.ui.bases

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseViewGroup<VB : ViewDataBinding>(context: Context, attributeSet: AttributeSet) :
    RelativeLayout(context, attributeSet) {

    lateinit var mBinding: VB

    init {
        val layoutView = this.getLayoutView()
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(context), layoutView, this, true)
        this.initViews()
        this.onResizeViews()
        this.onClickViews()
    }

    abstract fun getLayoutView(): Int

    open fun initViews() {}

    open fun onResizeViews() {}

    open fun onClickViews() {}
}