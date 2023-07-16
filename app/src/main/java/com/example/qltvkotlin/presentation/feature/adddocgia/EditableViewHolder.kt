package com.example.qltvkotlin.presentation.feature.adddocgia

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.qltvkotlin.databinding.ItemEditableBinding
import com.example.qltvkotlin.domain.enumeration.Command
import com.example.qltvkotlin.domain.enumeration.HasCommandCallback
import com.example.qltvkotlin.domain.enumeration.RemoveCmd
import com.example.qltvkotlin.domain.model.Updatable
import com.example.qltvkotlin.presentation.extension.bindingOf
import com.example.qltvkotlin.presentation.extension.cast
import com.example.qltvkotlin.presentation.extension.onClick

class EditableViewHolder(
    parent: ViewGroup,
    val binding: ItemEditableBinding = parent.bindingOf(ItemEditableBinding::inflate)
) : RecyclerView.ViewHolder(binding.root), Bindable<EditableField>, HasCommandCallback {
    private var item: EditableField? = null
    override var onCommand: (Command) -> Unit = {}
    private val textWatcher = binding.edtText.addTextChangedListener {
        item.cast<Updatable>()?.update(it.toString())
    }

    override fun bind(item: EditableField) {
        this.item = item
        binding.txtLabel.text = item.label
        binding.edtText.setTextWithoutNotify(item.value)
        binding.btnXoa.show(item is HasIsRemovable && item.isRemovable) {
            onClick {
                onCommand(RemoveCmd(item))
            }
        }
    }

    private fun EditText.setTextWithoutNotify(value: String) {
        removeTextChangedListener(textWatcher)
        setText(value)
        addTextChangedListener(textWatcher)
    }
}

fun <V : View> V.show(isShow: Boolean, function: V.() -> Unit) {
    visibility = if (isShow) {
        function()
        View.VISIBLE
    } else View.GONE
}

