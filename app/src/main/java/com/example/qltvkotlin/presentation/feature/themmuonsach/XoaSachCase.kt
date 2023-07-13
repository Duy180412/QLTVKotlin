package com.example.qltvkotlin.presentation.feature.themmuonsach

import com.example.qltvkotlin.domain.model.IMuonSach
import com.example.qltvkotlin.domain.model.IMuonSachSet
import com.example.qltvkotlin.domain.model.IThongTinSachThueGeneral
import com.example.qltvkotlin.presentation.extension.cast

class XoaSachCase {
    operator fun invoke(sach: IThongTinSachThueGeneral, newMuonSach: IMuonSach?) {
        val muonSach = newMuonSach.cast<IMuonSachSet>() ?: return
        val index = muonSach.list.indexOf(sach)
        muonSach.list.removeAt(index)
    }
}