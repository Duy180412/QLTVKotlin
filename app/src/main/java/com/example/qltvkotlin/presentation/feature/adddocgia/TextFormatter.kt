package com.example.qltvkotlin.presentation.feature.adddocgia

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TextFormatter {
    fun formatDate(date: Date): String {
        return SimpleDateFormat("MMM dd yyyy", Locale.getDefault()).format(date)
    }

    companion object {
        val shared: TextFormatter = TextFormatter()
    }

}