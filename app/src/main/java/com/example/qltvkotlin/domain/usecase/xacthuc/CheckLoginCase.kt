package com.example.qltvkotlin.domain.usecase.xacthuc

import com.example.qltvkotlin.data.datasource.AppStorage

class CheckLoginCase {
    val shared = AppStorage.instance
    operator fun invoke(): Boolean {
        return shared.isLogin()
    }
}