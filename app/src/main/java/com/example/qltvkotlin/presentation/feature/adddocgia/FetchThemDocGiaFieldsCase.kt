package com.example.qltvkotlin.presentation.feature.adddocgia

import androidx.lifecycle.MutableLiveData
import com.example.qltvkotlin.domain.model.Updatable
import com.example.qltvkotlin.domain.observable.Signal
import com.example.qltvkotlin.domain.observable.signal
import com.example.qltvkotlin.presentation.extension.post
import java.util.Date

class FetchThemDocGiaFieldsCase(
    private val resources: AppResources = AppResources.shared,
    private val textFormatter: TextFormatter = TextFormatter.shared,
    private val appConfig: AppConfig = AppConfig.shared,
) {

    val result = MutableLiveData<List<IComponent>>()

    private val originalFields = listOfNotNull(
        createPhotoField(),
        createEditable(StringId.Name),
        createEditable(StringId.Identify, true),
        if (appConfig.isUsePhoneNumber) createEditable(StringId.PhoneNumber) else null,
        createDatePicker(StringId.ExpireDate),
    )

    private fun createDatePicker(labelId: StringId): IComponent {
        return object : DateField, Updatable, Signal by signal(), HasDate {
            override val label: String = resources[labelId]
            override var date: Date = EmptyDate

            override fun update(value: Any?) {
                this.date = value as? Date ?: return
                emit()
            }

            override fun toString(): String {
                if (date is EmptyDate) return ""
                return textFormatter.formatDate(date)
            }
        }
    }

    operator fun invoke() {
        result.post(createFields())
    }

    private fun createFields(): List<IComponent> {
        return object : MutableList<IComponent> by originalFields.toMutableList(),
            Signal by signal(), HasAny, HasRemovedComponents {
            override val any: Any = originalFields
            override val removedComponents: List<IComponent> = arrayListOf()
        }
    }

    private fun createDivider(): IComponent {
        return object : DividerComponent {}
    }

    private fun createEditable(labelId: StringId, isRemovable: Boolean = false): IComponent {
        return object : EditableField, Updatable, HasFieldId, HasIsRemovable {
            override val label: String = resources[labelId]
            override var value: String = ""
            override val fieldId: StringId = labelId
            override val isRemovable: Boolean = isRemovable

            override fun update(value: Any?) {
                this.value = value?.toString() ?: return
            }
        }
    }

    private fun createPhotoField(): IComponent {
        return object : PhotoField, Updatable, Signal by signal() {
            override var image: Image = EmptyImage

            override fun update(value: Any?) {
                this.image = value as? Image ?: return
                emit()
            }
        }
    }

    fun shouldFetch(): Boolean {
        return result.value.isNullOrEmpty()
    }
}

