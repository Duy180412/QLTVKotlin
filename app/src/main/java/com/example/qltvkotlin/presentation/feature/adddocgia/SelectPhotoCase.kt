package com.example.qltvkotlin.presentation.feature.adddocgia

import android.graphics.Bitmap
import android.net.Uri
import com.example.qltvkotlin.domain.helper.AppNavigator
import com.example.qltvkotlin.domain.helper.DialogProvider
import com.example.qltvkotlin.domain.helper.SelectPhotoType
import com.example.qltvkotlin.domain.model.Updatable
import com.example.qltvkotlin.presentation.extension.cast
import com.example.qltvkotlin.presentation.helper.PermissionAccessible

class SelectPhotoCase(
    private val dialogProvider: DialogProvider = DialogProvider.shared,
    private val permissionAccessible: PermissionAccessible = PermissionAccessible.shared,
    private val appNavigator: AppNavigator = AppNavigator.shared
) {
    suspend operator fun invoke(field: PhotoField) {

        val newImage = when (dialogProvider.chonChupAnhHoacThuVienAnh()) {
            SelectPhotoType.Camera -> selectByCamera()
            SelectPhotoType.Gallery -> selectByGallery()
            else -> return
        }

        field.cast<Updatable>()?.update(newImage)
    }

    private suspend fun selectByGallery(): Image? {
        if (!permissionAccessible.accessReadFile()) return null
        val uri = appNavigator.selectPhotoByGallery() ?: return null
        return createUriImage(uri)
    }

    private suspend fun selectByCamera(): Image? {
        if (!permissionAccessible.accessCamera()) return null
        val bitmap = appNavigator.selectPhotoByCamera() ?: return null
        return object : Image, HasBitmap {
            override val bitmap: Bitmap = bitmap
        }
    }

    private fun createUriImage(uri: Uri) = object : Image, HasUri {
        override val uri: Uri = uri
    }
}