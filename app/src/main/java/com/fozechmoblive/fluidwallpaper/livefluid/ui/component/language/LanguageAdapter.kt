package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.language

import android.app.Activity
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import com.fozechmoblive.fluidwallpaper.livefluid.R

import com.fozechmoblive.fluidwallpaper.livefluid.databinding.ItemLanguageBinding
import com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.BaseRecyclerView

class LanguageAdapter(
    var activity: Activity, val onClickItemLanguage: (LanguageModel) -> Unit
) : BaseRecyclerView<LanguageModel>() {

    override fun getItemLayout() = R.layout.item_language

    override fun submitData(newData: List<LanguageModel>) {
        list.clear()
        list.addAll(newData)
        notifyDataSetChanged()
    }

    override fun setData(binding: ViewDataBinding, item: LanguageModel, layoutPosition: Int) {
        if (binding is ItemLanguageBinding) {
            context?.let { ctx ->
                binding.imgLanguage.setImageDrawable(item.image?.let { ctx.getDrawable(it) })
                binding.imgLanguage.borderColor = ContextCompat.getColor(ctx, R.color.color_9E9E9E)
                binding.tvTitleLanguage.text = item.languageName
                binding.checkboxLanguage.isChecked = item.isCheck

                if (item.isCheck){
                    binding.container.setBackgroundResource(R.drawable.border_item_language_select)
                    binding.tvTitleLanguage.setTextColor(activity.resources.getColor(R.color.white))
                }else{
                    binding.container.setBackgroundResource(R.drawable.border_item_language_un_select)
                    binding.tvTitleLanguage.setTextColor(activity.resources.getColor(R.color.black))
                }
            }
        }
    }


    override fun getItemCount(): Int {
        return list.size
    }

    override fun onResizeViews(binding: ViewDataBinding) {
        super.onResizeViews(binding)
        if (binding is ItemLanguageBinding) {
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.setMargins(5, 10, 5, 10)
            binding.root.layoutParams = lp
        }
    }

    override fun onClickViews(binding: ViewDataBinding, obj: LanguageModel, layoutPosition: Int) {
        super.onClickViews(binding, obj, layoutPosition)
        if (binding is ItemLanguageBinding) {
            binding.root.setOnClickListener { v: View? ->
                onClickItemLanguage(obj)
            }
            binding.checkboxLanguage.setOnClickListener { v: View? ->
                onClickItemLanguage(obj)
            }
        }
    }

    fun setSelectLanguage(model: LanguageModel) {
        for (data in list) {
            data.isCheck = data.languageName == model.languageName
        }
        notifyDataSetChanged()
    }

}