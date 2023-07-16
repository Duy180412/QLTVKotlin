package com.example.qltvkotlin.presentation.widget.actionbar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.qltvkotlin.R
import com.example.qltvkotlin.databinding.TopbarViewBinding
import com.example.qltvkotlin.domain.datastructure.pairLookupOf
import com.example.qltvkotlin.domain.usecase.themmuontra.ThemMuonThueFragment
import com.example.qltvkotlin.presentation.extension.onClick
import com.example.qltvkotlin.presentation.feature.adddocgia.ThemDocGiaFragment
import com.example.qltvkotlin.presentation.feature.addsach.ThemSachFragment
import com.example.qltvkotlin.presentation.feature.docgiainfo.InfoDocGiaFragment
import com.example.qltvkotlin.presentation.feature.infosach.ThongTinSachFragment
import kotlin.reflect.KClass

class ActionBarViewState(
    clazz: KClass<out Fragment>,
    actionBarExt: ActionBarExt
) {
    lateinit var onClickBack: () -> Unit
    lateinit var onClickEditAndSave: (View) -> Unit

    private var actionBarSate: ActionBarSate
    private val routing = pairLookupOf<KClass<out Fragment>, ActionBarSate>(
        ThemSachFragment::class to ActionBarSate(R.string.title_them_sach, true),
        ThongTinSachFragment::class to ActionBarSate(R.string.title_info_sach),
        ThemDocGiaFragment::class to ActionBarSate(R.string.title_them_docgia, true),
        InfoDocGiaFragment::class to ActionBarSate(R.string.title_info_docgia),
        ThemMuonThueFragment::class to ActionBarSate(R.string.title_them_muon, true)

    )

    init {
        actionBarSate = routing.requireValueOf(clazz)
        val tieuDe = ActionBarTitileView()
        actionBarExt.setState(tieuDe)
    }


    private inner class ActionBarTitileView :
        State {
        private var iTem = actionBarSate.iconShow
        private val title = actionBarSate.title
        override fun onCreate(inflater: LayoutInflater, parent: ViewGroup): ViewBinding {
            TopbarViewBinding.inflate(inflater, parent, false).apply {
                this.titleTop.setText(title)
                this.btnEdit.isSelected = iTem
                this.btnBack.onClick { onClickBack() }
                this.btnEdit.onClick { onClickEditAndSave(this.btnEdit) }
                return this
            }
        }

    }

}

class ActionBarSate(val title: Int, val iconShow: Boolean = false)