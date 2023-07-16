package com.example.qltvkotlin.domain.usecase.themmuontra

import com.example.qltvkotlin.domain.helper.DialogProvider
import com.example.qltvkotlin.domain.model.IMuonSach
import com.example.qltvkotlin.domain.model.IMuonSachSet
import com.example.qltvkotlin.domain.model.IThongTinSachThueGeneral
import com.example.qltvkotlin.domain.model.ThongTinSachThueSet
import com.example.qltvkotlin.presentation.extension.cast

class ThemSachCase(
    private val dialogProvider: DialogProvider = DialogProvider.shared
) {
    suspend operator fun invoke(
        thongTin: IThongTinSachThueGeneral,
        newMuonSach: IMuonSach?
    ) {
        if (thongTin !is ThongTinSachThueSet) return
        val sachDuocChon = dialogProvider.chonSach() ?: return

        val isExists = newMuonSach.cast<IMuonSachSet>()
            ?.list?.find { it.maSach.toString() == sachDuocChon.key } != null

        if (isExists) {
            dialogProvider.thongBao("Sách Này Đã Được Thêm Vào")
            return
        }

        with(thongTin) {
            maSach.update(sachDuocChon.key)
            tenSach.update(sachDuocChon.nameKey)
            soLuong.setMax(sachDuocChon.status)
        }
    }
}