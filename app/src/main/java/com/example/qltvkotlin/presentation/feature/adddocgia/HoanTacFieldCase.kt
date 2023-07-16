package com.example.qltvkotlin.presentation.feature.adddocgia

import com.example.qltvkotlin.domain.observable.Signal
import com.example.qltvkotlin.presentation.extension.cast

class HoanTacFieldCase {
    operator fun invoke(components: List<IComponent>) {

        val truongMoiDuocXoa = components.cast<HasRemovedComponents>()?.removedComponents
            ?.lastOrNull() ?: return

        val originalFields = components.cast<HasAny>()
            ?.any.cast<MutableList<IComponent>>() ?: return

        val truongMoiDuocXoaIndex = originalFields.indexOf(truongMoiDuocXoa)

        (components as MutableList).add(truongMoiDuocXoaIndex, truongMoiDuocXoa)

        components.cast<Signal>()?.emit()
    }
}