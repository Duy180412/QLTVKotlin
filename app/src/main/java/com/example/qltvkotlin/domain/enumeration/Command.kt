package com.example.qltvkotlin.domain.enumeration

import com.example.qltvkotlin.domain.model.IThongTinSachThueGeneral
import com.example.qltvkotlin.presentation.feature.adddocgia.PhotoField

interface Command
interface HasCommandCallback {
    var onCommand: (Command) -> Unit
}

class ThemSachCmd(val sach: IThongTinSachThueGeneral) : Command
object ThemMuonSachCmd : Command
class ThemDocGiaCmd() : Command
class XoaSachCmd(val sach: IThongTinSachThueGeneral) : Command
class ThemThemSachRongCmd() : Command

class OnClickItem(val key: String) : Command
class OnClickDel(val key: String) : Command

class SelectPhotoCmd(val field: PhotoField) : Command
object LuuDocGiaCmd : Command
object HoanTacCmd : Command
class RemoveCmd<T>(val item: T) : Command