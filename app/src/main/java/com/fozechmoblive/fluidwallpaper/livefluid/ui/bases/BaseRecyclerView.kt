package com.fozechmoblive.fluidwallpaper.livefluid.ui.bases

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerView<T> : RecyclerView.Adapter<BaseRecyclerView<T>.ViewHolder>() {

    abstract fun getItemLayout(): Int
    abstract fun submitData(newData: List<T>)
    abstract fun setData(binding: ViewDataBinding, item: T, layoutPosition: Int)
    open fun onResizeViews(binding: ViewDataBinding) {}
    open fun onClickViews(binding: ViewDataBinding, obj: T, layoutPosition: Int) {}

    val list: MutableList<T> = mutableListOf()
    var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                getItemLayout(), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(list[position])
    }

    override fun getItemCount(): Int {
        if (list.isNotEmpty()) {
            return list.size
        }
        return 0
    }


    inner class ViewHolder(var binding: ViewDataBinding) : BaseViewHolder<T>(binding) {

        override fun bindData(obj: T) {
            onResizeViews()
            onClickViews(obj)

            setData(binding, obj, layoutPosition)
        }

        override fun onResizeViews() {
            onResizeViews(binding)
        }

        override fun onClickViews(obj: T) {
            onClickViews(binding, obj, layoutPosition)
        }
    }

}