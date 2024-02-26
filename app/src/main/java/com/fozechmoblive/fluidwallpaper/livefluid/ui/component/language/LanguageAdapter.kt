package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.language

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fozechmoblive.fluidwallpaper.livefluid.R
import com.fozechmoblive.fluidwallpaper.livefluid.callback.CallBack

import com.fozechmoblive.fluidwallpaper.livefluid.databinding.ItemLanguageBinding
import com.fozechmoblive.fluidwallpaper.livefluid.extentions.hide
import com.fozechmoblive.fluidwallpaper.livefluid.extentions.setBackGroundDrawable

class LanguageAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var data: MutableList<LanguageActivity.Language>

    var currentPos: Int = 0

    private var callBackLanguage: CallBack.CallBackLanguage? = null

    fun callBackLanguage(callBackLanguage: CallBack.CallBackLanguage) {
        this.callBackLanguage = callBackLanguage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemLanguageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addAll(mData: MutableList<LanguageActivity.Language>, currentPos: Int) {
        this.data = mData
        this.currentPos = currentPos
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
        return data.size
    }

    inner class ViewHolder(private val binding: ItemLanguageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(position: Int) {
            binding.txtLanguageTitle.text = data[position].name
            binding.imLangLogo.setBackgroundResource(data[position].img)

            var language = ""
            if (currentPos == position) {
                binding.ivCheck.setBackGroundDrawable(R.drawable.ic_check_language_enable)
            } else {
                binding.ivCheck.setBackGroundDrawable(R.drawable.ic_check_language_disable)
            }

            if (position == data.size - 1) {
                binding.divider.hide()
            }

            binding.root.setOnClickListener {
                language = data[position].name
                callBackLanguage?.callBackLanguage(language, position)
            }
        }
    }
}