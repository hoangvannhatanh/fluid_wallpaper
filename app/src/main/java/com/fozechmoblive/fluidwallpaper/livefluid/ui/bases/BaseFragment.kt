package com.fozechmoblive.fluidwallpaper.livefluid.ui.bases

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.fozechmoblive.fluidwallpaper.livefluid.utils.EasyPreferences

abstract class BaseFragment<VB : ViewDataBinding> : Fragment() {
    lateinit var mBinding: VB
    lateinit var prefs: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        prefs = context?.let { EasyPreferences.defaultPrefs(it) }!!
        val layoutView = getLayoutFragment()
        mBinding = DataBindingUtil.inflate(inflater, layoutView, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        onResizeViews()
        onClickViews()
        observerData()
    }

    abstract fun getLayoutFragment(): Int

    open fun initViews() {}

    open fun onClickViews() {}

    open fun onResizeViews() {}

    open fun observerData() {}
}