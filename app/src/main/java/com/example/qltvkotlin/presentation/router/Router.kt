package com.example.qltvkotlin.presentation.router

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.example.qltvkotlin.presentation.feature.mainnavigation.MainNavigationActivity


object Router {
    fun open(ower: LifecycleOwner, routing: Routing) {
        startActivity(ower, routing)
    }

    private fun startActivity(ower: LifecycleOwner, routing: Routing) {
        when (ower) {
            is Activity -> ower.startActivity(createIntent(ower, routing))
            is Fragment -> ower.startActivity(createIntent(ower.requireContext(), routing))
        }
    }

    private fun createIntent(context: Context, routing: Routing): Intent {
        val intent = when (routing) {
            is ActivityRouting -> Intent(context, routing.clazzActivity.java).apply {
                putExtras(
                    routing.toBundle()
                )
            }

            is FragmentRouting -> Intent(context, MainNavigationActivity::class.java).apply {
                putExtras(routing.toBundle())
            }

            else -> error("Routing invalid")
        }
        return intent
    }
}
