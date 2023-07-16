package com.example.qltvkotlin.presentation.feature.adddocgia

import com.example.qltvkotlin.R
import com.example.qltvkotlin.domain.helper.ActivityRetriever

class AppResources(private val activityRetriever: ActivityRetriever) {
    operator fun get(id: StringId): String {
        return when (id) {
            StringId.Name -> R.string.name
            StringId.Identify -> R.string.identify
            StringId.PhoneNumber -> R.string.phone_number
            StringId.ExpireDate -> R.string.expired_date
        }.let { activityRetriever().getString(it) }
    }

    companion object {
        val shared: AppResources = AppResources(ActivityRetriever.shared)
    }
}