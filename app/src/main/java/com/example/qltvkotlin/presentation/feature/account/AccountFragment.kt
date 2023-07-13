package com.example.qltvkotlin.presentation.feature.account

import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.qltvkotlin.R
import com.example.qltvkotlin.presentation.app.MainFragment
import com.example.qltvkotlin.presentation.extension.viewmodel
import com.example.qltvkotlin.data.datasource.AppStorage
import com.example.qltvkotlin.presentation.router.Router
import com.example.qltvkotlin.presentation.router.Routes
import com.example.qltvkotlin.presentation.router.Routing


class AccountFragment : MainFragment(R.layout.fragment_account) {
    override val viewmodel by viewmodel<VM>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity.actionBarMain.logout = {
            viewmodel.logOut()
        }
        viewmodel.login.observe(viewLifecycleOwner){
            Router.open(this, Routes.Login())
        }
    }


    class VM : BaseViewModel() {
        private val shared = AppStorage.instance
        val login = MutableLiveData<Routing>()
        fun logOut() {
            shared.logOut()
            login.postValue(Routes.Login())
        }
    }
}