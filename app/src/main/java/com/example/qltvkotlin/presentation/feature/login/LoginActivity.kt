package com.example.qltvkotlin.presentation.feature.login

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.example.qltvkotlin.domain.observable.Signal
import com.example.qltvkotlin.R
import com.example.qltvkotlin.presentation.app.BaseActivity
import com.example.qltvkotlin.presentation.extension.viewBinding
import com.example.qltvkotlin.presentation.extension.viewModel
import com.example.qltvkotlin.databinding.ActivityLoginBinding
import com.example.qltvkotlin.domain.model.HasIsValid
import com.example.qltvkotlin.domain.model.IAccount
import com.example.qltvkotlin.domain.repo.FetchAccountCase
import com.example.qltvkotlin.domain.usecase.LoginCase
import com.example.qltvkotlin.presentation.extension.launch
import com.example.qltvkotlin.presentation.extension.bindTo
import com.example.qltvkotlin.presentation.extension.cast
import com.example.qltvkotlin.presentation.extension.onClick

class LoginActivity : BaseActivity(R.layout.activity_login), Signal.Closable by Signal.Bags() {
    private val binding by viewBinding { ActivityLoginBinding.bind(this) }
    private val viewModel by viewModel<VM>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.buttonlogin.onClick { viewModel.login() }
        binding.idlogin.bindTo { viewModel.account.value?.id }
        binding.pass.bindTo { viewModel.account.value?.password }

        viewModel.account.observe(this, this::bind)
    }

    private fun bind(it: IAccount) {
        binding.idlogin.setText(it.id)
        binding.pass.setText(it.password)
        it.id.cast<Signal>()?.bind {
            binding.idlogin.isActivated = !(it.id.cast<HasIsValid>()?.isValid ?: true)
        }
        it.password.cast<Signal>()?.bind {
            binding.pass.isActivated = !(it.password.cast<HasIsValid>()?.isValid ?: true)
        }
    }

    class VM(
        private val loginCase: LoginCase = LoginCase(),
        private val fetchAccountCase: FetchAccountCase = FetchAccountCase()
    ) : ViewModel() {
        val account = fetchAccountCase.result

        init {
            launch { fetchAccountCase() }
        }

        fun login() {
            val mValue = account.value ?: return
            launch { loginCase(mValue) }
        }
    }
}