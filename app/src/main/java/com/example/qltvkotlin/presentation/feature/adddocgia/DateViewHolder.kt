package com.example.qltvkotlin.presentation.feature.adddocgia

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.recyclerview.widget.RecyclerView
import com.example.qltvkotlin.databinding.ItemDateBinding
import com.example.qltvkotlin.databinding.ItemDividerBinding
import com.example.qltvkotlin.domain.model.Updatable
import com.example.qltvkotlin.domain.observable.Signal
import com.example.qltvkotlin.domain.observable.closable
import com.example.qltvkotlin.presentation.extension.bindingOf
import com.example.qltvkotlin.presentation.extension.cast
import com.example.qltvkotlin.presentation.extension.onClick
import java.util.Calendar
import java.util.Date

class DateViewHolder(
    parent: ViewGroup,
    val binding: ItemDateBinding = parent.bindingOf(ItemDateBinding::inflate)
) : RecyclerView.ViewHolder(binding.root), Bindable<DateField>, Signal.Closable by closable() {

    override fun bind(item: DateField) {
        binding.txtDate.text = item
        binding.txtLabel.text = item.label

        item.cast<Signal>()?.bind {
            binding.txtDate.text = item
        }
        binding.txtDate.onClick {
            showDatePickerDialog {
                item.cast<Updatable>()?.update(it)
            }
        }
    }

    private fun showDatePickerDialog(function: (Date) -> Unit) {
        val cal = Calendar.getInstance()
        DatePickerDialog(
            itemView.context,
            { _, year, month, dayOfMonth ->
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                function(cal.time)
            },
            cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH),
        ).show()
    }
}