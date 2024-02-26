package com.fozechmoblive.fluidwallpaper.livefluid.ui.bases.ext

import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.os.Build
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.style.CharacterStyle
import android.text.style.URLSpan
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat

fun TextView.setTextById(idString: Int) {
    text = resources.getString(idString)
}

fun TextView.setTextByString(string: String) {
    text = string
}

val TextView.value: String get() = text.toString().trim()

fun TextView.underlineText() {
    paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
}

fun TextView.removeUnderlines() {
    val spannable = SpannableString(text)
    for (u in spannable.getSpans(0, spannable.length, URLSpan::class.java)) {
        spannable.setSpan(object : URLSpan(u.url) {
            override fun updateDrawState(textPaint: TextPaint) {
                super.updateDrawState(textPaint)
                textPaint.isUnderlineText = false
            }
        }, spannable.getSpanStart(u), spannable.getSpanEnd(u), 0)
    }
    text = spannable
}

fun TextView.setTextColorById(idColor: Int) {
    setTextColor(ContextCompat.getColor(context, idColor))
}

fun setTextGradient(textView: TextView, listColor: ArrayList<Int>) {

    val arr: IntArray = listColor.stream().mapToInt { i -> i }.toArray()
    val textShader: Shader = LinearGradient(
        0f,
        0f,
        textView.paint.measureText(textView.text.toString()),
        textView.textSize,
        arr,
        floatArrayOf(0f, 1f),
        Shader.TileMode.CLAMP
    )
    textView.paint.shader = textShader
}


@RequiresApi(Build.VERSION_CODES.N)
fun setTextGradient(
    textView: TextView, text: String?, start: Int, end: Int, listColor: ArrayList<Int>
) {
    // Chuỗi chứa văn bản và gradient color
    val arr: IntArray = listColor.stream().mapToInt { i -> i }.toArray()
    val spannableString = SpannableString(text)

    // Tạo một Shader dùng để tạo gradient
    val shader: Shader = LinearGradient(
        0f, 0f, 0f, textView.textSize, arr, null, Shader.TileMode.CLAMP
    )

    // Tạo một CharacterStyle để áp dụng gradient
    val style: CharacterStyle = object : CharacterStyle() {
        override fun updateDrawState(paint: TextPaint) {
            paint.shader = shader
        }
    }

    // Áp dụng gradient color cho phần văn bản từ start đến end
    spannableString.setSpan(style, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE)

    // Hiển thị spannableString trên TextView
    textView.text = spannableString
}