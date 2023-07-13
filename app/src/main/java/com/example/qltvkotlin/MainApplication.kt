package com.example.qltvkotlin

import android.app.Application
import com.example.qltvkotlin.data.datasource.AppStorage
import com.example.qltvkotlin.data.datasource.roomdata.AppDataBase
import com.example.qltvkotlin.domain.helper.ActivityRetriever
import com.example.qltvkotlin.presentation.helper.AppFileManager
import com.example.qltvkotlin.presentation.widget.HorizontalLineDecoration


class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ActivityRetriever.shared = ActivityRetriever(this)
        AppDataBase.init(this)
        AppFileManager.init(this)
        AppStorage.instance.init(this)
        HorizontalLineDecoration.item = HorizontalLineDecoration(this)
    }
}