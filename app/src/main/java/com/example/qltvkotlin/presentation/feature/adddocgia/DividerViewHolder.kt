package com.example.qltvkotlin.presentation.feature.adddocgia

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.qltvkotlin.databinding.ItemDividerBinding
import com.example.qltvkotlin.presentation.extension.bindingOf

class DividerViewHolder(
    parent: ViewGroup,
    val binding: ItemDividerBinding = parent.bindingOf(ItemDividerBinding::inflate)
) : RecyclerView.ViewHolder(binding.root) {}

