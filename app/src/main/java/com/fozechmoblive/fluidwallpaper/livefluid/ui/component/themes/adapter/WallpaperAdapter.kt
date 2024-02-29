package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.themes.adapter

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.fozechmoblive.fluidwallpaper.livefluid.R

import com.fozechmoblive.fluidwallpaper.livefluid.databinding.ItemWallpaperBinding
import com.fozechmoblive.fluidwallpaper.livefluid.models.PresetModel
import com.fozechmoblive.fluidwallpaper.livefluid.models.Status
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.BaseRecyclerView
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.ext.click
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.ext.goneView
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.ext.visibleView
import com.fozechmoblive.fluidwallpaper.livefluid.utils.TypePresetModel


class WallpaperAdapter(
    var presetName: String, val onClickItemSound: (PresetModel, Int) -> Unit
) : BaseRecyclerView<PresetModel>() {
    private val mListData = ArrayList<PresetModel>()

    override fun getItemLayout() = R.layout.item_wallpaper

    override fun submitData(newData: List<PresetModel>) {
        val diffResult = DiffUtil.calculateDiff(WallpaperDiffUtil(newData, list))
        list.clear()
        list.addAll(newData)
        diffResult.dispatchUpdatesTo(this)
    }

    fun setCheckNewItem(presetName: String) {
        this.presetName = presetName
        notifyDataSetChanged()
    }

    override fun setData(binding: ViewDataBinding, item: PresetModel, layoutPosition: Int) {
        if (binding is ItemWallpaperBinding) {
            context?.let { ctx ->

                if (item.typePresetModel == TypePresetModel.ADS) {
                    binding.lBgr.goneView()
                } else {
                    binding.lBgr.visibleView()

                    if (item.typePresetModel == TypePresetModel.CUSTOM) {
                        Glide.with(ctx).load(item.pathImageCustom).into(binding.imagePreset)
                    } else {
                        Glide.with(ctx).load(item.imagePreset).diskCacheStrategy(
                            DiskCacheStrategy.DATA
                        ).into(binding.imagePreset)
                    }


                    binding.textNamePreset.text = item.name
                    if (list[layoutPosition].name == presetName) {
                        binding.checkItemSelected.visibleView()
                        binding.imvApplySelected.visibleView()
                    } else {
                        binding.checkItemSelected.goneView()
                        binding.imvApplySelected.goneView()
                    }
                }

            }
        }
    }


    override fun getItemCount(): Int {
        return list.size
    }

    override fun onResizeViews(binding: ViewDataBinding) {
        super.onResizeViews(binding)
    }

    override fun onClickViews(binding: ViewDataBinding, obj: PresetModel, layoutPosition: Int) {
        super.onClickViews(binding, obj, layoutPosition)
        if (binding is ItemWallpaperBinding) {
            binding.root.click { v: View? ->
                if (obj.typePresetModel != TypePresetModel.ADS) {
                    onClickItemSound(obj, layoutPosition)
                }
            }

        }
    }


    fun setMList(list: List<PresetModel>) {
        mListData.clear()
        mListData.addAll(list)
    }

    fun filterTypePosition(type: TypePresetModel) {
        when (type) {

            TypePresetModel.CUSTOM -> {
                submitData(addItemAds(mListData.filter { it.typePresetModel == TypePresetModel.CUSTOM }))
            }

            TypePresetModel.NEW -> {
                submitData(addItemAds(mListData.filter { it.typePresetModel == TypePresetModel.NEW }))
            }

            TypePresetModel.TRENDING -> {
                submitData(addItemAds(mListData.filter { it.typePresetModel == TypePresetModel.TRENDING }))
            }

            TypePresetModel.FEATURE -> {
                submitData(addItemAds(mListData.filter { it.typePresetModel == TypePresetModel.FEATURE }))
            }

            TypePresetModel.ALL -> {
                submitData(mListData)
            }

            TypePresetModel.ADS -> {

            }
        }
    }

    private fun addItemAds(listData: List<PresetModel>): List<PresetModel> {

        val itemsToAdd = ArrayList<PresetModel>()

        for (i in listData.indices) {
            itemsToAdd.add(listData[i])
            if ((i + 1) % 9 == 0) {
                val newItem = PresetModel(
                    R.drawable.bg_preset_vip1,
                    "Ads",
                    Status.FREE,
                    false,
                    typePresetModel = TypePresetModel.ADS
                )
                itemsToAdd.add(newItem)
            }
        }
        return itemsToAdd

    }


}