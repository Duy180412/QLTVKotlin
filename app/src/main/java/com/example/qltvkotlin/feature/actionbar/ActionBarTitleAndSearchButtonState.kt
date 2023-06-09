package com.example.qltvkotlin.feature.actionbar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.example.qltvkotlin.databinding.TopbarTitleSearchBinding
import com.example.qltvkotlin.feature.presentation.extension.onClick

class ActionBarTitleAndSearchButtonState(private val title: Int) : State {
    lateinit var clickSearch: () -> Unit
    override fun onCreate(inflater: LayoutInflater, parent: ViewGroup): ViewBinding {
        TopbarTitleSearchBinding.inflate(inflater, parent, false).apply {
            this.tenFragment.setText(title)
            this.btnSearch.onClick { clickSearch() }
            return this
        }
    }
}