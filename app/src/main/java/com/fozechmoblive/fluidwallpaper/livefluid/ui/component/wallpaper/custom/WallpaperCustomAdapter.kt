package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.wallpaper.custom

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.fozechmoblive.fluidwallpaper.livefluid.R
import com.fozechmoblive.fluidwallpaper.livefluid.databinding.ItemWallpaperCustomBinding
import com.fozechmoblive.fluidwallpaper.livefluid.models.PresetModel
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.BaseRecyclerView
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.ext.click
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.ext.goneView
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.ext.visibleView
import com.fozechmoblive.fluidwallpaper.livefluid.ui.component.themes.adapter.WallpaperDiffUtil


class WallpaperCustomAdapter(var choosePosition :Int, val onClickItemSound: (String, Int) -> Unit
) : BaseRecyclerView<PresetModel>() {
    private val mListData = ArrayList<PresetModel>()

    override fun getItemLayout() = R.layout.item_wallpaper_custom

    override fun submitData(newData: List<PresetModel>) {
        val diffResult = DiffUtil.calculateDiff(WallpaperDiffUtil(newData, list))
        list.clear()
        list.addAll(newData)
        diffResult.dispatchUpdatesTo(this)
        notifyItemChanged(0)
    }

    override fun setData(binding: ViewDataBinding, item: PresetModel, layoutPosition: Int) {
        if (binding is ItemWallpaperCustomBinding) {
            context?.let { ctx ->
                
                Glide.with(ctx).load(item.imagePreset).diskCacheStrategy(
                    DiskCacheStrategy.DATA).into(binding.imagePreset)

                binding.textNamePreset.text = item.name
                if (layoutPosition == choosePosition) {
                    binding.checkItemSelected.visibleView()
                    binding.imvApplySelected.visibleView()
                } else {
                    binding.checkItemSelected.goneView()
                    binding.imvApplySelected.goneView()
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
        if (binding is ItemWallpaperCustomBinding) {
            binding.root.click { v: View? ->
                choosePosition = layoutPosition
                notifyDataSetChanged()
                onClickItemSound(obj.name, layoutPosition)
            }

        }
    }



    fun setMList(list: List<PresetModel>) {
        mListData.clear()
        mListData.addAll(list)
    }

}