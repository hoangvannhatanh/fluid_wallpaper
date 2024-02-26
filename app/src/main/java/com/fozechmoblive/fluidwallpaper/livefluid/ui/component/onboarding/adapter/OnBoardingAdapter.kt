package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.onboarding.adapter

import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.fozechmoblive.fluidwallpaper.livefluid.R

import com.fozechmoblive.fluidwallpaper.livefluid.databinding.ItemOnBoardingBinding
import com.fozechmoblive.fluidwallpaper.livefluid.models.GuideModel
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.BaseRecyclerView


class OnBoardingAdapter(
) : BaseRecyclerView<GuideModel>() {

    override fun getItemLayout(): Int = R.layout.item_on_boarding

    override fun setData(binding: ViewDataBinding, item: GuideModel, layoutPosition: Int) {
        if (binding is ItemOnBoardingBinding) {
            binding.tvTitle.text = context?.getString(item.title)
            binding.tvSubText.text = context?.getString(item.subText)
            context?.let {
                Glide.with(it).load(context?.getDrawable(item.img))
                    .into(binding.imgGuide)
            }
        }
    }

    override fun submitData(newData: List<GuideModel>) {
        list.clear()
        list.addAll(newData)
        notifyDataSetChanged()
    }
}