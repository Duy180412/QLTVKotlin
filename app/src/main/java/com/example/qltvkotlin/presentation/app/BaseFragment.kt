package com.example.qltvkotlin.presentation.app

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.example.qltvkotlin.domain.model.MessageShowOwner
import com.example.qltvkotlin.domain.helper.DialogProvider
import com.example.qltvkotlin.presentation.helper.AppPermissionOwer
import com.example.qltvkotlin.presentation.model.SingleLiveEvent
import com.example.qltvkotlin.presentation.widget.view.ToastFactoryOwner

abstract class BaseFragment(contentLayoutId: Int) : Fragment(contentLayoutId),
    AppPermissionOwer, ToastFactoryOwner, MessageShowOwner {
    val dialogProvider = DialogProvider.shared

    abstract val viewmodel: BaseViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel.error.observe(viewLifecycleOwner) { throws ->
            throws.message?.let { dialogProvider.notification(it) }
        }

        viewmodel.thongBao.observe(viewLifecycleOwner) {
            thongBaoToast(it)
        }
    }

    fun thongBaoToast(it: String) {
        toast.invoke(it)
    }

    abstract class BaseViewModel : ViewModel() {
        val error = SingleLiveEvent<Throwable>()
        val successAndFinish = SingleLiveEvent<String>()
        val thongBao = SingleLiveEvent<String>()
    }
}