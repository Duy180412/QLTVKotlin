package com.example.qltvkotlin.domain.usecase

import com.example.qltvkotlin.domain.enumeration.StringId
import com.example.qltvkotlin.domain.helper.DialogProvider
import com.example.qltvkotlin.domain.model.HasValueKey
import com.example.qltvkotlin.domain.model.IAddView
import com.example.qltvkotlin.domain.model.IComponent
import com.example.qltvkotlin.domain.model.IInputLayoutField
import com.example.qltvkotlin.domain.model.Updatable
import com.example.qltvkotlin.domain.repo.MuonThueRepo
import com.example.qltvkotlin.presentation.extension.cast
import com.example.qltvkotlin.presentation.widget.IItemSpinner
import com.example.qltvkotlin.presentation.widget.fields.ClickAddField
import com.example.qltvkotlin.presentation.widget.fields.SelectTextFeild

class SelecDocGiaMuonSachCase(
    private val dialogProvider: DialogProvider = DialogProvider.shared,
    private val muonthueRepo: MuonThueRepo = MuonThueRepo.shared
) {
    suspend operator fun invoke(it: IInputLayoutField, list: List<IComponent>) {
        if (!coTheSua()) return
        val docGiaDuocChon = dialogProvider.chonDocGia() ?: return
        if (daDuocChon(list, docGiaDuocChon)) throw Exception("Đọc Giả Này Đã Được Chọn")
        val isExitsDocGia = muonthueRepo.isExitsMuonSach(docGiaDuocChon.key)
        if (isExitsDocGia) throw Exception("Độc Giả Này Đã Đăng Kí Mượn Sách")
        if (!isExitsAddView(list)) (list as? ArrayList)?.add(list.size, ClickAddField())
        it.cast<Updatable>()?.update(docGiaDuocChon.nameKey)
        it.cast<HasValueKey>()?.key = docGiaDuocChon.key
    }

    private fun isExitsAddView(list: List<IComponent>): Boolean {
        list.forEach {
            if (it is IAddView) return true
        }
        return false
    }

    private fun coTheSua(): Boolean {
        return true
    }

    private fun daDuocChon(list: List<IComponent>, docGiaDuocChon: IItemSpinner): Boolean {
        val editable = hashMapOf<StringId, String>()
        list.forEach {
            when (it) {
                is SelectTextFeild -> editable[it.getFieldsID()] = it.key.toString()
            }
        }
        val daDuocChon = editable[StringId.DocGiaMuon] ?: return false
        return docGiaDuocChon.key == daDuocChon
    }
}