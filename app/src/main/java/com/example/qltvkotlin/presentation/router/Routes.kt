package com.example.qltvkotlin.presentation.router


import android.app.Activity
import androidx.fragment.app.Fragment
import com.example.qltvkotlin.presentation.feature.login.LoginActivity
import com.example.qltvkotlin.presentation.feature.mainnavigation.MainActivity
import com.example.qltvkotlin.presentation.feature.adddocgia.ThemDocGiaFragment
import com.example.qltvkotlin.presentation.feature.docgiainfo.InfoDocGiaFragment
import com.example.qltvkotlin.presentation.feature.themmuonsach.ThemMuonThueFragment
import com.example.qltvkotlin.presentation.feature.addsach.ThemSachFragment
import com.example.qltvkotlin.presentation.feature.infosach.ThongTinSachFragment
import kotlinx.parcelize.Parcelize
import kotlin.reflect.KClass


object Routes {


    @Parcelize
    class Main : ActivityRouting {
        override val clazzActivity: KClass<out Activity>
            get() = MainActivity::class
    }

    @Parcelize
    class Login : ActivityRouting {
        override val clazzActivity: KClass<out Activity>
            get() = LoginActivity::class
    }

    @Parcelize
    class AddSach : FragmentRouting {
        override val clazzFragment: KClass<out Fragment>
            get() = ThemSachFragment::class
    }

    @Parcelize
    class InfoSach(val id: String) : FragmentRouting {
        override val clazzFragment: KClass<out Fragment>
            get() = ThongTinSachFragment::class
    }
    @Parcelize
    class AddDocGia : FragmentRouting {
        override val clazzFragment: KClass<out Fragment>
            get() = ThemDocGiaFragment::class
    }

    @Parcelize
    class InfoDocGia(val id: String) : FragmentRouting {
        override val clazzFragment: KClass<out Fragment>
            get() = InfoDocGiaFragment::class
    }
    @Parcelize
    class AddMuon : FragmentRouting {
        override val clazzFragment: KClass<out Fragment>
            get() = ThemMuonThueFragment::class
    }


}