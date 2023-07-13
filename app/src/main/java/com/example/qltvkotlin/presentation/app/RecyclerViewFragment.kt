package com.example.qltvkotlin.presentation.app

import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.qltvkotlin.feature.helper.Results
import com.example.qltvkotlin.presentation.extension.launch

abstract class RecyclerViewFragment(contentLayoutId: Int) : MainFragment(contentLayoutId) {
    abstract override val viewmodel: FragmentViewVM


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity.actionBarMain.search.observe(viewLifecycleOwner) {
            viewmodel.search(it)
        }
        viewmodel.result.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Results.Loading -> result.message?.let { thongBaoToast(it) }
                is Results.Success -> {
                    result.value ?: return@observe
                    dialogProvider.bottomUndo(requireView(), result.message) {
                        viewmodel.khoiPhuc(result.value)
                    }
                }

                is Results.Error -> result.message?.let { thongBaoToast(it) }
            }
        }
    }


    override fun onResume() {
        super.onResume()
        viewmodel.restoreSearch()
    }

    abstract class FragmentViewVM : BaseViewModel() {
        var result = MutableLiveData<Results<String>>()
        var searchType = ""
        abstract fun search(it: String)
        fun restoreSearch() {
            search(searchType)
        }

        fun khoiPhuc(id: String) {
            result.value = Results.Loading()
            launch {
                val boolean = repo(id)
                if (boolean) {
                    result.postValue(Results.Success("Khôi Phục Thành Công", null))
                    restoreSearch()
                } else result.postValue(Results.Error())
            }
        }

        fun xoa(id: String) {
            result.value = Results.Loading()
            launch {
                val boolean = del(id)
                if (boolean) {
                    result.postValue(Results.Success("Xóa Thành Công", id))
                    restoreSearch()
                } else result.postValue(Results.Error())
            }
        }

        abstract suspend fun repo(id: String): Boolean
        abstract suspend fun del(id: String): Boolean

    }
}