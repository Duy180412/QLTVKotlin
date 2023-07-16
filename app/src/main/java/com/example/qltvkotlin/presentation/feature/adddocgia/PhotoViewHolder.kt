package com.example.qltvkotlin.presentation.feature.adddocgia

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.qltvkotlin.databinding.ItemPhotoBinding
import com.example.qltvkotlin.domain.enumeration.Command
import com.example.qltvkotlin.domain.enumeration.HasCommandCallback
import com.example.qltvkotlin.domain.enumeration.SelectPhotoCmd
import com.example.qltvkotlin.domain.observable.Signal
import com.example.qltvkotlin.domain.observable.closable
import com.example.qltvkotlin.presentation.extension.bindingOf
import com.example.qltvkotlin.presentation.extension.cast
import com.example.qltvkotlin.presentation.extension.onClick
import com.example.qltvkotlin.presentation.extension.setImage

class PhotoViewHolder(
    parent: ViewGroup,
    val binding: ItemPhotoBinding = parent.bindingOf(ItemPhotoBinding::inflate)
) : RecyclerView.ViewHolder(binding.root),
    Bindable<PhotoField>,
    HasCommandCallback,
    Signal.Closable by closable() {

    override var onCommand: (Command) -> Unit = {}

    override fun bind(item: PhotoField) {
        binding.imgPhoto.setImage(item.image)
        binding.imgPhoto.onClick {
            onCommand(SelectPhotoCmd(item))
        }
        item.cast<Signal>()?.bind {
            binding.imgPhoto.setImage(item.image)
        }
    }
}


