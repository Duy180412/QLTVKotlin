package com.example.qltvkotlin.presentation.feature.adddocgia

import com.example.qltvkotlin.domain.observable.Signal
import com.example.qltvkotlin.presentation.extension.cast

class RemoveFieldCase {
    operator fun invoke(field: EditableField, components: List<IComponent>) {
        val fieldPosition = components.indexOf(field)
        if (fieldPosition < 0) return
        if (components !is MutableList) return

        components.removeAt(fieldPosition)
        (components.cast<HasRemovedComponents>()?.removedComponents as? MutableList)?.add(field)
        components.cast<Signal>()?.emit()
    }
}