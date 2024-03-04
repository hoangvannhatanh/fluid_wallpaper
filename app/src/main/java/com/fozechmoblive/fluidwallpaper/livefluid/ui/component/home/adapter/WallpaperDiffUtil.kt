package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.home.adapter

import androidx.recyclerview.widget.DiffUtil
import com.fozechmoblive.fluidwallpaper.livefluid.models.PresetModel

class WallpaperDiffUtil(
    private val newList: List<PresetModel>,
    private val oldList: List<PresetModel>
) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].typePresetModel == newList[newItemPosition].typePresetModel
                && oldList[oldItemPosition].isNew == newList[newItemPosition].isNew
                && oldList[oldItemPosition].isSelected == newList[newItemPosition].isSelected
                && oldList[oldItemPosition].imagePreset == newList[newItemPosition].imagePreset
                && oldList[oldItemPosition].isLock == newList[newItemPosition].isLock
                && oldList[oldItemPosition].name == newList[newItemPosition].name
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}