package com.example.qltvkotlin.domain.helper

import android.app.AlertDialog
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.qltvkotlin.presentation.extension.cast
import com.example.qltvkotlin.presentation.feature.help.dialogcustom.DocGiaSelecDialog
import com.example.qltvkotlin.presentation.feature.help.dialogcustom.SachSelecDialog
import com.example.qltvkotlin.presentation.widget.spinner.IItemSpinner
import com.example.qltvkotlin.presentation.widget.view.ToastFactoryOwner
import com.google.android.material.snackbar.Snackbar
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class DialogProvider(
    private val activityRetriever: ActivityRetriever = ActivityRetriever.shared
) {
    companion object {
        val shared: DialogProvider = DialogProvider()
    }

    fun selectYesNo(
        text: String,
        onAccept: () -> Unit,
        onCancel: () -> Unit
    ): AlertDialog {
        val diaLog = AlertDialog.Builder(activityRetriever())
        diaLog.setMessage(text)
        diaLog.setPositiveButton("Yes") { d, _ ->
            onAccept.invoke()
            d.dismiss()
        }
        diaLog.setNegativeButton("No") { d, _ ->
            onCancel.invoke()
            d.dismiss()

        }
        return diaLog.show()
    }

    fun notification(
        text: String,
    ): AlertDialog {
        val diaLog = AlertDialog.Builder(activityRetriever())
        diaLog.setTitle("Error")
        diaLog.setMessage(text)
        diaLog.setPositiveButton("Ok") { d, _ ->
            d.dismiss()
        }
        return diaLog.show()
    }

    fun bottomUndo(view: View, it: String, function: () -> Unit) {
        val snackbar = Snackbar.make(view, it, Snackbar.LENGTH_LONG)
        snackbar.setAction("Undo") {
            function.invoke()
        }
        return snackbar.show()
    }

    suspend fun chonSach(): IItemSpinner? {
        val activity = activityRetriever() as? AppCompatActivity ?: return null
        return suspendCoroutine { con ->
            SachSelecDialog(activity).apply {
                dialog?.setOnCancelListener {
                    con.resume(null)
                }
            }.showDialog {
                con.resume(it)
            }
        }
    }

    suspend fun chonDocGia(): IItemSpinner? {
        val activity = activityRetriever() as? AppCompatActivity ?: return null

        return suspendCoroutine { con ->
            DocGiaSelecDialog(activity).apply {
                dialog?.setOnCancelListener {
                    con.resume(null)
                }
            }.showDialog {
                con.resume(it)
            }
        }
    }

    fun thongBao(message: String) {
        activityRetriever().cast<ToastFactoryOwner>()?.toast?.invoke(message)
    }

    suspend fun chonChupAnhHoacThuVienAnh(): SelectPhotoType? {
        return suspendCoroutine { con ->
            AlertDialog.Builder(activityRetriever())
                .setPositiveButton("Gallery") { _, _ ->
                    con.resume(SelectPhotoType.Gallery)
                }
                .setNegativeButton("Camera") { _, _ ->
                    con.resume(SelectPhotoType.Camera)
                }
                .setOnCancelListener {
                    con.resume(null)
                }
                .create().show()
        }
    }
}

