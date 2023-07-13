package com.example.qltvkotlin.data.datasource

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.qltvkotlin.domain.model.IAccount

class AppStorage {
    private var shared: SharedPreferences? = null

    fun init(context: Application) {
        shared = context.getSharedPreferences("cache", Context.MODE_PRIVATE)
    }

    companion object {
        val instance = AppStorage()
    }

    private fun checkAccount(value: IAccount): Boolean {
        return value.id.toString() == "taikhoan" && value.password.toString() == "matkhau"
    }

    fun isLoggedIn(value: IAccount): Boolean {
        return if (checkAccount(value)) {
            shared?.edit { putString("login", "ok") }
            true
        } else false
    }

    fun isLogin(): Boolean {
        return !shared?.getString("login","").isNullOrBlank()
    }
    fun logOut(){
        shared?.edit { remove("login") }
    }

}