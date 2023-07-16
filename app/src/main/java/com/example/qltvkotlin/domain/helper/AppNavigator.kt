package com.example.qltvkotlin.domain.helper

import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity
import com.example.qltvkotlin.presentation.router.Router
import com.example.qltvkotlin.presentation.router.Routes
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AppNavigator(private val activityRetriever: ActivityRetriever = ActivityRetriever.shared) {
    fun openMain() {
        Router.open(activityRetriever(), Routes.Main())
    }

    fun openLogin() {
        Router.open(activityRetriever(), Routes.Login())
    }

    fun closeCurrent() {
        activityRetriever().finish()
    }

    suspend fun selectPhotoByCamera(): Bitmap? {
        val activity = activityRetriever() as? FragmentActivity ?: return null
        return suspendCoroutine { con ->
            var launcher: ActivityResultLauncher<Void?>? = null
            launcher = activity.activityResultRegistry.register(
                "open_camera",
                ActivityResultContracts.TakePicturePreview()
            ) {
                launcher?.unregister()
                con.resume(it)
            }
            launcher.launch(null)
        }
    }

    suspend fun selectPhotoByGallery(): Uri? {
        val activity = activityRetriever() as? FragmentActivity ?: return null
        return suspendCoroutine { con ->
            var launcher: ActivityResultLauncher<String>? = null
            launcher = activity.activityResultRegistry
                .register("open_gallery", ActivityResultContracts.GetContent()) {
                    launcher?.unregister()
                    con.resume(it)
                }
            launcher.launch("image/*")
        }
    }

    companion object {
        val shared: AppNavigator = AppNavigator()
    }

}