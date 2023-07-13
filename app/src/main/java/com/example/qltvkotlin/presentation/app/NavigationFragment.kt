package com.example.qltvkotlin.presentation.app

import android.os.Bundle
import android.view.View
import com.example.qltvkotlin.presentation.extension.require
import com.example.qltvkotlin.presentation.helper.action.TakePhotoActionOwner
import com.example.qltvkotlin.presentation.helper.OnBackClick
import com.example.qltvkotlin.presentation.feature.mainnavigation.MainNavigationActivity

abstract class NavigationFragment(contentLayoutId: Int) : BaseFragment(contentLayoutId),
    TakePhotoActionOwner {
    val navigationActivity get() = activity.require<MainNavigationActivity>()

    private lateinit var onBackClick: OnBackClick

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBackClick = OnBackClick(requireActivity())
        onBackClickCustom()
        navigationActivity.actionBarView.onClickEditAndSave = { it -> clickEditAndSave(it) }
    }

    abstract fun clickEditAndSave(it: View)

    private fun onBackClickCustom() {
        onBackClick.checkValueWhenClickBack(
            funCheck = getCheck(),
            funRun = getRun()
        )
        navigationActivity.actionBarView.onClickBack = { onBackClick.handleOnBackPressed() }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackClick)
    }

    private fun getRun(): () -> Unit {
        return { dialogProvider.selectYesNo("Hủy Thêm", { navigationActivity.finish() }, {}) }
    }

    abstract fun getCheck(): () -> Boolean

}