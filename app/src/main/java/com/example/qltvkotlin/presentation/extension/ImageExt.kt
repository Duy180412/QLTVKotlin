package com.example.qltvkotlin.presentation.extension

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.qltvkotlin.presentation.feature.adddocgia.HasBitmap
import com.example.qltvkotlin.presentation.feature.adddocgia.HasUri
import com.example.qltvkotlin.presentation.feature.adddocgia.Image


fun ImageView.setImage(image: Image) {
    val glide = Glide.with(this)

    val manager = when (image) {
        is HasUri -> glide.load(image.uri)
        is HasBitmap -> glide.load(image.bitmap)
        else -> null
    }
    manager?.into(this)
}
