package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.themes.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.fozechmoblive.fluidwallpaper.livefluid.databinding.ItemThemeViewPagerBinding
import com.fozechmoblive.fluidwallpaper.livefluid.models.PresetModel
import java.util.*

class ThemeNewUpdateAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var listTheme: MutableList<PresetModel>
    var currentPos: Int = 0

//    private var callBackLanguage: CallBack_APPNTD.CallBackLanguage? = null
//
//    fun callBackLanguage(callBackLanguage: CallBack_APPNTD.CallBackLanguage) {
//        this.callBackLanguage = callBackLanguage
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemThemeViewPagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addAll(listTheme: MutableList<PresetModel>) {
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

    inner class ViewHolder(private val binding: ItemThemeViewPagerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(position: Int) {
            Glide.with(binding.root.context).load(listTheme[position].imagePreset).diskCacheStrategy(
                DiskCacheStrategy.DATA
            ).into(binding.imageSlide)

//            binding.root.setOnClickListener {
//                callBackLanguage?.callBackLanguage(language, position)
//            }
        }
    }
}