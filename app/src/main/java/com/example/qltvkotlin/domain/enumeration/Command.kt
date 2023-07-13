package com.example.qltvkotlin.domain.enumeration

import com.example.qltvkotlin.domain.model.IThongTinSachThueGeneral

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