package com.example.qltvkotlin.domain.helper

import com.example.qltvkotlin.presentation.router.Router
import com.example.qltvkotlin.presentation.router.Routes

class AppNavigator(private val activityRetriever: ActivityRetriever = ActivityRetriever.shared) {
    fun openMain() {
        Router.open(activityRetriever(), Routes.Main())
    }

    fun openLogin() {
        Router.open(activityRetriever(), Routes.Login())
    }

    fun closeCurrent() {
        activityRetriever().finish()
    }

    companion object {
        val shared: AppNavigator = AppNavigator()
    }

}