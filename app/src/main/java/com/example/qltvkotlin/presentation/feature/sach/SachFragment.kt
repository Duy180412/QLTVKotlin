package com.example.qltvkotlin.presentation.feature.sach

import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.qltvkotlin.R
import com.example.qltvkotlin.databinding.FragmentSachBinding
import com.example.qltvkotlin.domain.model.ISachItem
import com.example.qltvkotlin.domain.repo.SachRepo
import com.example.qltvkotlin.presentation.app.RecyclerViewFragment
import com.example.qltvkotlin.presentation.extension.launch
import com.example.qltvkotlin.presentation.extension.viewBinding
import com.example.qltvkotlin.presentation.extension.viewmodel
import com.example.qltvkotlin.presentation.extension.onClick
import com.example.qltvkotlin.presentation.extension.show
import com.example.qltvkotlin.domain.enumeration.Command
import com.example.qltvkotlin.domain.enumeration.OnClickDel
import com.example.qltvkotlin.domain.enumeration.OnClickItem
import com.example.qltvkotlin.presentation.router.Router
import com.example.qltvkotlin.presentation.router.Routes


class SachFragment : RecyclerViewFragment(R.layout.fragment_sach) {
    private val binding by viewBinding { FragmentSachBinding.bind(this) }
    override val viewmodel by viewmodel<VM>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = SachApdater(binding.rycView)
        viewmodel.search.observe(viewLifecycleOwner) {
            show(binding.rong, it.isEmpty())
            adapter.setList(it)
        }
        binding.btnAdd.onClick {
            Router.open(this, Routes.AddSach())
        }
        adapter.onCommand = { actionCommand(it) }
    }


    private fun actionCommand(it: Command) {
        when (it) {
            is OnClickItem -> Router.open(this, Routes.InfoSach(it.key))
            is OnClickDel -> viewmodel.xoa(it.key)
            else -> return
        }
    }

    class VM : FragmentViewVM() {
        private val sachRepo = SachRepo.shared
        var search = MutableLiveData<List<ISachItem>>()

        override fun search(it: String) {
            searchType = it
            launch {
                search.postValue(sachRepo.searchSach(it))
            }
        }

        override suspend fun repo(id: String): Boolean {
            return sachRepo.repoSach(id)
        }

        override suspend fun del(id: String): Boolean {
            return sachRepo.delSach(id)
        }
    }
}

