package com.example.qltvkotlin.presentation.app

import androidx.appcompat.app.AppCompatActivity
import com.example.qltvkotlin.presentation.widget.view.ToastFactoryOwner

abstract class BaseActivity(layoutId: Int) : AppCompatActivity(layoutId),
    ToastFactoryOwner