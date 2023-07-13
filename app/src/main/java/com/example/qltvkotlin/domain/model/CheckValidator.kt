package com.example.qltvkotlin.domain.model

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.example.qltvkotlin.domain.observable.IDestroyObsever
import com.example.qltvkotlin.domain.observable.Signal
import com.example.qltvkotlin.presentation.extension.cast
import com.google.android.material.textfield.TextInputLayout


fun <T> bindOnChange(ownr: LifecycleOwner, it: T, mValue: (T) -> Unit) {
    val closeable = it.cast<Signal>()?.subscribe { mValue.invoke(it) }
    val checkOwnr = if (ownr is Fragment) ownr.viewLifecycleOwner else ownr
    checkOwnr.lifecycle.addObserver(object : IDestroyObsever {
        override fun onDestroyed() {
            closeable?.close()
        }
    })
}

fun checkAndShowError(it: CharSequence, editText: TextInputLayout) {
    val value = it.cast<Validable>()?.validate()!!
    editText.isErrorEnabled = !value
    val error = if (!value) it.cast<GetError>()?.getMess() else return
    editText.error = error
}

fun checkConditionChar(vararg it: CharSequence): Boolean {
    for (value in it) {
        if (value !is Validable) return false
        val check = value.cast<Validable>()?.validate()!!
        if (!check) return false
    }
    return true
}

infix fun Boolean.checkValueThrowError(message: String) {
    if (this) throw IllegalArgumentException(message)
}

interface MessageShowOwner {
    val message: MessageShow
        get() = MessageShow()
}

class MessageShow {
    val errorSystem = " Lỗi hệ thống"
    val charsEmpty = " Không thể để trống các mục bắt buộc"
    val isExist = " Đã tồn tại "
    val wrongFormat = " Không chấp nhận kiểu giá trị này"
    val saveHasImage = " Đã lưu có hình ảnh"
    val saveNoImage = " Đã lưu không có hình ảnh"
    val delSuccess = " Đã Xóa Thành Công "
    val delFailure = " Không Thể Xóa "
    val delSuccessMain = " Đã Xóa Thành Công Ở Danh Sách Chính "
    val delFailureMain = " Không Thể Xóa Ở Danh Sách Chính "
    val undoSuccess = "Đã Hoàn Tác "
    val undoFailure = "Không Thể Hoàn Tác "
}

enum class MessageId(val value: String) {
    errorSystem(" Lỗi hệ thống"),
    charsEmpty(" Không thể để trống các mục bắt buộc"),
    isExist(" Đã tồn tại "),
    wrongFormat(" Không chấp nhận kiểu giá trị này"),
    saveHasImage(" Đã lưu có hình ảnh"),
    saveNoImage(" Đã lưu không có hình ảnh"),
    delSuccess(" Đã Xóa Thành Công "),
    delFailure(" Không Thể Xóa "),
    delSuccessMain(" Đã Xóa Thành Công Ở Danh Sách Chính "),
    delFailureMain(" Không Thể Xóa Ở Danh Sách Chính "),
    undoSuccess("Đã Hoàn Tác "),
    undoFailure("Không Thể Hoàn Tác "),
}