package com.fozechmoblive.fluidwallpaper.livefluid.utils

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RatingBar
import com.bumptech.glide.Glide
import com.fozechmoblive.fluidwallpaper.livefluid.R
import com.fozechmoblive.fluidwallpaper.livefluid.databinding.DialogRatingBinding

@SuppressLint("SetTextI18n")
class RatingDialog(private val context: Context) : Dialog(context) {
    private lateinit var binding: DialogRatingBinding
    private var onPress: OnPress? = null

    init {
        binding = DialogRatingBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)

        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        window?.setGravity(Gravity.CENTER)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        setCancelable(true)
        changeRating()
        onClick()

        binding.tvTitle.text = "${context.getString(R.string.do_you_like)} ${context.getString(R.string.app_name)}?"
    }

    interface OnPress {
        fun send()
        fun rating()
        fun cancel()
        fun later()
    }

    fun init(onPress: OnPress) {
        this.onPress = onPress
    }

    private fun changeRating() {
        binding.rbRate.onRatingBarChangeListener =
            RatingBar.OnRatingBarChangeListener { ratingBar, rating, fromUser ->
                if (binding.rbRate.rating == 5f) {
                    binding.imgStateRate.let {
                        Glide.with(context).load(R.drawable.img_rate_5).into(
                            it
                        )
                    }
                }
                if (binding.rbRate.rating == 4f) {
                    binding.imgStateRate.let {
                        Glide.with(context).load(R.drawable.img_rate_4).into(
                            it
                        )
                    }
                }
                if (binding.rbRate.rating == 3f) {
                    binding.imgStateRate.let {
                        Glide.with(context).load(R.drawable.img_rate_3).into(
                            it
                        )
                    }
                }
                if (binding.rbRate.rating == 2f) {
                    binding.imgStateRate.let {
                        Glide.with(context).load(R.drawable.img_rate_2).into(
                            it
                        )
                    }
                }
                if (binding.rbRate.rating == 1f) {
                    binding.imgStateRate.let {
                        Glide.with(context).load(R.drawable.img_rate_1).into(
                            it
                        )
                    }
                }
                if (binding.rbRate.rating == 0f) {
                    binding.imgStateRate.let {
                        Glide.with(context).load(R.drawable.img_rate_0).into(
                            it
                        )
                    }
                }
            }
    }

    private fun onClick() {
        binding.tvRate.setOnClickListener {
            if (binding.rbRate.rating == 1f) {
                onPress?.send()
            }
            if (binding.rbRate.rating == 2f) {
                onPress?.send()
            }
            if (binding.rbRate.rating == 3f) {
                onPress?.send()
            }
            if (binding.rbRate.rating == 4f) {
                onPress?.send()
            }
            if (binding.rbRate.rating == 5f) {
                onPress?.rating()
            }
            if (binding.rbRate.rating == 0f) {
                return@setOnClickListener
            }
        }
        binding.tvNoLater.setOnClickListener {
            onPress?.later()
        }
    }
}