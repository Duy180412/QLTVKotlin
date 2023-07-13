package com.example.qltvkotlin.presentation.feature.muonthue

import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.qltvkotlin.R
import com.example.qltvkotlin.databinding.FragmentMuonthueViewBinding
import com.example.qltvkotlin.domain.model.IMuonSachItem
import com.example.qltvkotlin.domain.repo.MuonThueRepo
import com.example.qltvkotlin.presentation.app.RecyclerViewFragment
import com.example.qltvkotlin.presentation.extension.launch
import com.example.qltvkotlin.presentation.extension.viewBinding
import com.example.qltvkotlin.presentation.extension.viewmodel
import com.example.qltvkotlin.presentation.extension.show
import com.example.qltvkotlin.domain.enumeration.Role

class DangThueFragment : ViewMuonSachFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewmodel.loaiShare = Role.DangThue
    }
}

class HetHanFragment : ViewMuonSachFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewmodel.loaiShare = Role.HetHan
    }

}

abstract class ViewMuonSachFragment : RecyclerViewFragment(R.layout.fragment_muonthue_view) {
    private val binding by viewBinding { FragmentMuonthueViewBinding.bind(this) }
    override val viewmodel by viewmodel<VM>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = MuonSachApdater(binding.rycView)
        adapter.onClickDel = {
            dialogProvider.selectYesNo("Trả Toàn Bộ Sách",{viewmodel.xoa(it)},{})
        }

        viewmodel.search.observe(viewLifecycleOwner) {
            adapter.setList(it)
            show(binding.rong, it.isEmpty())
        }
    }
    class VM : FragmentViewVM() {
        private val muonThueRepo = MuonThueRepo.shared
        var search = MutableLiveData<List<IMuonSachItem>>()
        var loaiShare: Role = Role.DangThue
        override fun search(it: String) {
            searchType = it
            launch {
                search.postValue(muonThueRepo.search(it, loaiShare))
            }
        }

        override suspend fun repo(id: String): Boolean {
           return muonThueRepo.repo(id)
        }

        override suspend fun del(id: String): Boolean {
            return muonThueRepo.del(id)
        }

    }

}