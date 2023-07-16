package com.example.qltvkotlin.domain.usecase.themmuontra

import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.qltvkotlin.R
import com.example.qltvkotlin.data.datasource.bo.MuonSachEdittable
import com.example.qltvkotlin.databinding.FragmentAddMuonThueBinding
import com.example.qltvkotlin.domain.enumeration.Command
import com.example.qltvkotlin.domain.enumeration.ThemDocGiaCmd
import com.example.qltvkotlin.domain.enumeration.ThemMuonSachCmd
import com.example.qltvkotlin.domain.enumeration.ThemSachCmd
import com.example.qltvkotlin.domain.enumeration.ThemThemSachRongCmd
import com.example.qltvkotlin.domain.enumeration.XoaSachCmd
import com.example.qltvkotlin.domain.model.HasChange
import com.example.qltvkotlin.domain.model.IMuonSach
import com.example.qltvkotlin.domain.model.IMuonSachGeneral
import com.example.qltvkotlin.domain.model.IMuonSachGet
import com.example.qltvkotlin.domain.model.bindOnChange
import com.example.qltvkotlin.domain.model.checkAndShowError
import com.example.qltvkotlin.domain.model.checkConditionChar
import com.example.qltvkotlin.presentation.app.NavigationFragment
import com.example.qltvkotlin.presentation.extension.cast
import com.example.qltvkotlin.presentation.extension.launch
import com.example.qltvkotlin.presentation.extension.show
import com.example.qltvkotlin.presentation.extension.viewBinding
import com.example.qltvkotlin.presentation.extension.viewmodel
import com.example.qltvkotlin.presentation.feature.themmuonsach.DangKiMuonSachAdapter


class ThemMuonThueFragment : NavigationFragment(R.layout.fragment_add_muon_thue) {
    private val binding by viewBinding { FragmentAddMuonThueBinding.bind(this) }
    override val viewmodel by viewmodel<VM>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = DangKiMuonSachAdapter(binding.rycView)
        adapter.onCommand = {
            viewmodel.execute(it)
        }
        binding.themdocgia.setStartIconOnClickListener {
            viewmodel.execute(ThemDocGiaCmd())
        }
        viewmodel.newMuonSach.observe(viewLifecycleOwner) {
            val value = it.cast<IMuonSachGeneral>()!!
            adapter.setList(value.list)
            bindWhenOnChange(value)
        }
    }

    private fun bindWhenOnChange(value: IMuonSachGeneral) {
        value.tenDocGia.also { it ->
            bindOnChange(this, it) {
                binding.themdocgianhap.setText(it.toString())
            }
        }
        value.maDocGia.also { it2 ->
            checkAndShowError(it2, binding.themdocgia)
            bindOnChange(this, it2) {
                checkAndShowError(it, binding.themdocgia)
                show(binding.rycView, checkConditionChar(it))
            }
        }
    }

    override fun clickEditAndSave(it: View) {
        viewmodel.execute(ThemMuonSachCmd)
    }

    override fun getCheck(): () -> Boolean {
        return { viewmodel.checkHasChange() }
    }

    class VM(
        private val themSachCase: ThemSachCase = ThemSachCase(),
        private val themDocGiaCase: ThemDocGiaCase = ThemDocGiaCase(),
        private val xoaSachCase: XoaSachCase = XoaSachCase(),
        private val themSachRongCase: ThemSachRongCase = ThemSachRongCase(),
        private val themMuonSachCase: ThemMuonSachCase = ThemMuonSachCase(),
    ) : BaseViewModel() {
        var newMuonSach = MutableLiveData<IMuonSach>()

        init {
            newMuonSach.value = MuonSachEdittable(object : IMuonSachGet {})
        }

        fun execute(it: Command) {
            when (it) {
                is ThemSachCmd -> launch { themSachCase(it.sach, newMuonSach.value) }
                is ThemDocGiaCmd -> launch { themDocGiaCase(newMuonSach.value) }
                is XoaSachCmd -> launch { xoaSachCase(it.sach, newMuonSach.value) }
                is ThemThemSachRongCmd -> launch { themSachRongCase(newMuonSach.value) }
                is ThemMuonSachCmd -> launch { themMuonSachCase(newMuonSach.value) }
            }
        }

        fun checkHasChange(): Boolean {
            return newMuonSach.value.cast<HasChange>()?.hasChange()!!
        }
    }
}