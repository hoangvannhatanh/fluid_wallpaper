package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.fozechmoblive.fluidwallpaper.livefluid.callback.CallBack
import com.fozechmoblive.fluidwallpaper.livefluid.databinding.ItemThemeNewUpdateBinding
import com.fozechmoblive.fluidwallpaper.livefluid.models.PresetModel

class ThemeFeatureAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var listTheme: List<PresetModel>

    var currentPos: Int = 0

    private var callBackTheme: CallBack.CallBackTheme? = null

    fun callBackTheme(callBackTheme: CallBack.CallBackTheme) {
        this.callBackTheme = callBackTheme
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemThemeNewUpdateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addAll(listTheme: List<PresetModel>) {
        this.listTheme = listTheme
    }

    fun checkSelectView(pos: Int) {
        val oldPos = currentPos
        currentPos = pos
        notifyItemChanged(pos)
        notifyItemChanged(oldPos)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bindData(position)
    }

    override fun getItemCount(): Int {
        return listTheme.size
    }

    inner class ViewHolder(private val binding: ItemThemeNewUpdateBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(position: Int) {
            Glide.with(binding.root.context).load(listTheme[position].imagePreset).diskCacheStrategy(
                DiskCacheStrategy.DATA
            ).into(binding.imageSlide)

            binding.root.setOnClickListener {
                callBackTheme?.callBackTheme(listTheme[position])
            }
        }
    }
}