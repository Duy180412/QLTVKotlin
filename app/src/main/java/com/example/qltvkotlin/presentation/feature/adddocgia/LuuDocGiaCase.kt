package com.example.qltvkotlin.presentation.feature.adddocgia

import com.example.qltvkotlin.domain.helper.DialogProvider
import com.example.qltvkotlin.domain.model.Validable
import com.example.qltvkotlin.presentation.extension.cast
import com.google.gson.Gson
import java.util.Date

class LuuDocGiaCase(
    private val dialogProvider: DialogProvider = DialogProvider.shared,
) {
    suspend operator fun invoke(fields: List<IComponent>) {
        var photo: Image = EmptyImage
        var date: Date = EmptyDate

        val editable = hashMapOf<StringId, String>()

        val isAllValid = fields.all {
            val isValid = it.cast<Validable>()?.validate() ?: true
            isValid
        }

        if (!isAllValid) error("Form is not valid, please re-check it")

        fields.forEach {
            when (it) {
                is PhotoField -> photo = it.image
                is EditableField -> if (it is HasFieldId) editable[it.fieldId] = it.value
                is DateField -> date = it.cast<HasDate>()?.date ?: EmptyDate
            }
        }

        dialogProvider.thongBao(Gson().toJson(editable.map { "${it.key} -> ${it.value}" }
            .joinToString("\n")) + "Date: $date")

    }
}

