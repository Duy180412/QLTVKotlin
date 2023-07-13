package com.example.qltvkotlin.presentation.feature.themmuonsach

import com.example.qltvkotlin.domain.helper.DialogProvider
import com.example.qltvkotlin.domain.model.IMuonSach
import com.example.qltvkotlin.domain.model.IMuonSachSet
import com.example.qltvkotlin.presentation.extension.cast

class ThemDocGiaCase(
    private val dialogProvider: DialogProvider = DialogProvider.shared,
) {
    suspend operator fun invoke(newMuonSach: IMuonSach?) {
        val muonSach = newMuonSach.cast<IMuonSachSet>() ?: return
        val docGia = dialogProvider.chonDocGia() ?: return
        muonSach.maDocGia.update(docGia.key)
        muonSach.tenDocGia.update(docGia.nameKey)
    }
}