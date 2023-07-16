package com.example.qltvkotlin.presentation.feature.adddocgia

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.qltvkotlin.R
import com.example.qltvkotlin.databinding.FragmentAddDocGiaBinding
import com.example.qltvkotlin.domain.enumeration.Command
import com.example.qltvkotlin.domain.enumeration.HoanTacCmd
import com.example.qltvkotlin.domain.enumeration.LuuDocGiaCmd
import com.example.qltvkotlin.domain.enumeration.RemoveCmd
import com.example.qltvkotlin.domain.enumeration.SelectPhotoCmd
import com.example.qltvkotlin.presentation.app.BaseFragment
import com.example.qltvkotlin.presentation.extension.launch
import com.example.qltvkotlin.presentation.extension.onClick
import com.example.qltvkotlin.presentation.extension.viewBinding

class ThemDocGiaFragment : BaseFragment(R.layout.fragment_add_doc_gia) {

    private val binding by viewBinding(FragmentAddDocGiaBinding::bind)
    private val viewModel by viewModels<VM>()
    override val viewmodel: BaseViewModel get() = viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ComponentAdapter()

        viewModel.components.observe(viewLifecycleOwner) {
            adapter.submit(it)
        }

        adapter.onCommand = {
            viewModel.execute(it)
        }

        binding.btnLuu.onClick {
            viewModel.execute(LuuDocGiaCmd)
        }
        binding.btnHoanTac.onClick {
            viewModel.execute(HoanTacCmd)
        }

        binding.rvList.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        viewModel.tryFetch()
    }

    class VM(
        private val fetchThemDocGiaFieldsCase: FetchThemDocGiaFieldsCase = FetchThemDocGiaFieldsCase(),
        private val selectPhotoCase: SelectPhotoCase = SelectPhotoCase(),
        private val luuDocGiaCase: LuuDocGiaCase = LuuDocGiaCase(),
        private val removeFieldCase: RemoveFieldCase = RemoveFieldCase(),
        private val hoanTacFieldCase: HoanTacFieldCase = HoanTacFieldCase(),
    ) : BaseViewModel() {
        val components = fetchThemDocGiaFieldsCase.result

        fun tryFetch() {
            if (fetchThemDocGiaFieldsCase.shouldFetch()) launch {
                fetchThemDocGiaFieldsCase()
            }
        }

        fun execute(it: Command) {
            when (it) {
                is SelectPhotoCmd -> launch { selectPhotoCase(it.field) }
                is LuuDocGiaCmd -> launch { luuDocGiaCase(components.value.orEmpty()) }
                is RemoveCmd<*> -> launch {
                    removeFieldCase(
                        it.item as EditableField,
                        components.value.orEmpty()
                    )
                }

                is HoanTacCmd -> launch { hoanTacFieldCase(components.value.orEmpty()) }
            }
        }
    }
}


