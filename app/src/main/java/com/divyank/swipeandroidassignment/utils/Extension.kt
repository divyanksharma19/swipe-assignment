package com.divyank.swipeandroidassignment.utils

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.toast(msg: String?) {
    Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
}


fun Uri.isValidImage(context: Context): Boolean {
    val contentResolver = context.contentResolver
    val mimeType = contentResolver.getType(this)

    if (mimeType != "image/jpeg" && mimeType != "image/png" && mimeType != "image/jpg") {
        Toast.makeText(context, "Please select an image in JPEG or PNG format.", Toast.LENGTH_LONG).show()
        return false
    }

    // Decode image dimensions safely
    try {
        contentResolver.openInputStream(this)?.use { inputStream ->
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeStream(inputStream, null, options)

            if (options.outWidth != options.outHeight) {
                Toast.makeText(context, "Please select an image with a 1:1 aspect ratio.", Toast.LENGTH_LONG).show()
                return false
            }
        }
    } catch (e: Exception) {
        Toast.makeText(context, "Failed to process the image.", Toast.LENGTH_LONG).show()
        return false
    }

    return true
}
