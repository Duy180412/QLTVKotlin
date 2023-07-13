package com.example.qltvkotlin.presentation.feature.themmuonsach

import com.example.qltvkotlin.domain.helper.AppNavigator
import com.example.qltvkotlin.domain.helper.DialogProvider
import com.example.qltvkotlin.domain.model.IMuonSach
import com.example.qltvkotlin.domain.model.IMuonSachSet
import com.example.qltvkotlin.domain.model.MessageId
import com.example.qltvkotlin.domain.model.checkConditionChar
import com.example.qltvkotlin.domain.model.checkValueThrowError
import com.example.qltvkotlin.domain.repo.MuonThueRepo
import com.example.qltvkotlin.presentation.extension.cast

class ThemMuonSachCase(
    private val appNavigator: AppNavigator = AppNavigator.shared,
    private val dialogProvider: DialogProvider = DialogProvider.shared,
    private val muonThueRepo: MuonThueRepo = MuonThueRepo.shared,
) {
    suspend operator fun invoke(newMuonSach: IMuonSach?) {
        val value = newMuonSach.cast<IMuonSachSet>() ?: return
        val checkListNull = value.list.isNotEmpty()
        val listCheck = value.list.flatMap { listOf(it.maSach, it.soLuong) }.toTypedArray()
        val checkError = checkConditionChar(value.maDocGia, *listCheck) || checkListNull
        !checkError checkValueThrowError MessageId.errorSystem.value
        val checkExist = muonThueRepo.checkMuonSach(value.maDocGia.toString())
        checkExist checkValueThrowError value.tenDocGia.toString() + MessageId.isExist
        muonThueRepo.save(value)
        dialogProvider.thongBao("Thêm Mượn Sách")
        appNavigator.closeCurrent()
    }
}