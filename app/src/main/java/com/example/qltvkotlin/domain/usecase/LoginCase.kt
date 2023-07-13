package com.example.qltvkotlin.domain.usecase

import com.example.qltvkotlin.data.datasource.AppStorage
import com.example.qltvkotlin.domain.helper.AppNavigator
import com.example.qltvkotlin.domain.model.IAccount
import com.example.qltvkotlin.domain.model.Validable
import com.example.qltvkotlin.presentation.extension.cast

class LoginCase(
    private val appStorage: AppStorage = AppStorage.instance,
    private val appNavigator: AppNavigator = AppNavigator.shared
) {

    operator fun invoke(value: IAccount) {
        val isValid = arrayOf(value.id, value.password).all {
            it.cast<Validable>()?.validate() ?: true
        }
        if (!isValid) throw LoginFailException()

        if (appStorage.isLoggedIn(value)) {
            appNavigator.openMain()
            appNavigator.closeCurrent()
            return
        }
    }
}