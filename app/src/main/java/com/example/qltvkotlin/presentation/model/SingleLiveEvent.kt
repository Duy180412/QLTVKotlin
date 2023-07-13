package com.example.qltvkotlin.presentation.model

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class SingleLiveEvent<T> : MutableLiveData<T>() {
    private var isNotified = false

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner) {
            if (!isNotified) observer.onChanged(it)
            isNotified = true
        }
    }

    override fun setValue(value: T) {
        isNotified = false
        super.setValue(value)
    }
}