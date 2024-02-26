package com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.ext

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions


fun ImageView.loadImage(context: Context, path: Any, radius: Int = 1) {
    val options: RequestOptions = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .fitCenter()
        .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(radius)))

    Glide.with(context)
        .load(path)
        .apply(options)
        .into(this)
}

fun ImageView.loadImageNoCache(context: Context, path: Any, radius: Int = 1) {
    val options: RequestOptions = RequestOptions()
        .apply(
            RequestOptions().transform(
                CenterCrop(),
                RoundedCorners(radius)
            )
        )

    Glide.with(context)
        .load(path)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .skipMemoryCache(true)
        .apply(options)
        .into(this)
}

