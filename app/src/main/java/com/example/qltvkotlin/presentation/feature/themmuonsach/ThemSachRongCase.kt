package com.example.qltvkotlin.presentation.feature.themmuonsach

import com.example.qltvkotlin.domain.model.IMuonSach
import com.example.qltvkotlin.domain.model.IMuonSachSet
import com.example.qltvkotlin.domain.model.ThongTinSachThueSet
import com.example.qltvkotlin.presentation.extension.cast

class ThemSachRongCase {
    operator fun invoke(newMuonSach: IMuonSach?) {
        val muonSach = newMuonSach.cast<IMuonSachSet>() ?: return
        muonSach.list.add(ThongTinSachThueSet())
    }
}