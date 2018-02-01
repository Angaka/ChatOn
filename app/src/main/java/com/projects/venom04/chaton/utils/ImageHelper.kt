package com.projects.venom04.chaton.utils

import android.graphics.Bitmap
import java.io.ByteArrayOutputStream

/**
 * Created by beau-oudong on 01/02/2018.
 */
class ImageHelper {
    companion object {
        fun encodeImageToByteArray(bitmap: Bitmap?): ByteArray? {
            val baos = ByteArrayOutputStream()
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 50, baos)
            return baos.toByteArray()
        }
    }
}