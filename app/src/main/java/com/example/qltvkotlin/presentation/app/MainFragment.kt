package com.example.qltvkotlin.presentation.app

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.example.qltvkotlin.presentation.feature.mainnavigation.MainActivity

abstract class MainFragment(contentLayoutId: Int) : BaseFragment(contentLayoutId) {

    val mainActivity: MainActivity
        get() {
            return requireActivity() as? MainActivity
                ?: throw ClassCastException("Fragment ${this::class.java.simpleName} can't be cast to Activty ${T::class.java.simpleName}")
        }

}