package com.example.swiftyproteins.presentation

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.util.Log
import android.view.PixelCopy
import android.view.SurfaceView
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorRes
import androidx.fragment.app.Fragment
import com.example.swiftyproteins.domain.models.Atom
import com.google.ar.sceneform.math.Vector3
import java.lang.Exception
import android.app.KeyguardManager
import android.app.admin.DevicePolicyManager
import android.content.Context.KEYGUARD_SERVICE
import android.content.Intent
import android.os.*
import android.view.View
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.hardware.fingerprint.FingerprintManagerCompat
import androidx.core.view.isVisible

fun Fragment.getColor(@ColorRes colorResId: Int): Int =
    requireContext().getColor(colorResId)

fun Fragment.hideKeyboard() {
    val inputManager: InputMethodManager? =
        activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
    inputManager?.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
}

fun Fragment.getBitmapFromView(view: SurfaceView, onSuccess: (Bitmap) -> Unit) {
    val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
    val locationOfViewInWindow = IntArray(2)
    view.getLocationInWindow(locationOfViewInWindow)
    try {
        PixelCopy.request(view, bitmap, { copyResult ->
            if (copyResult == PixelCopy.SUCCESS) {
                onSuccess(bitmap)
            }
        }, Handler(Looper.getMainLooper()))
    } catch (e: IllegalArgumentException) {
        logE(e.message ?: "", e)
    }
}

fun Fragment.isDeviceLocked(): Boolean =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        (requireActivity().getSystemService(KEYGUARD_SERVICE) as KeyguardManager).isDeviceSecure
    } else {
        true
    }

fun Fragment.setupPassLock() {
    val intent = Intent(DevicePolicyManager.ACTION_SET_NEW_PASSWORD)
    startActivity(intent)
}

fun Bitmap.overlayToCenter(overlayBitmap: Bitmap): Bitmap {
    val bitmap1Width = width
    val bitmap1Height = height
    val bitmap2Width = overlayBitmap.width
    val bitmap2Height = overlayBitmap.height
    val maxWidth = if (bitmap1Width > bitmap2Width) {
        bitmap1Width
    } else {
        bitmap2Width
    }
    val maxHeight = if (bitmap1Height > bitmap2Height) {
        bitmap1Height
    } else {
        bitmap2Height
    }
    val marginLeft = (maxWidth * 0.5 - bitmap2Width * 0.5).toFloat()
    val marginTop = (maxHeight * 0.5 - bitmap2Height * 0.5).toFloat()
    val finalBitmap = Bitmap.createBitmap(maxWidth, maxHeight, config)
    val canvas = Canvas(finalBitmap)
    canvas.drawBitmap(this, Matrix(), null)
    canvas.drawBitmap(overlayBitmap, marginLeft, marginTop, null)
    return finalBitmap
}

fun View.hide() {
    isVisible = false
}

fun Any.logE(message: String?, e: Exception? = null) {
    val logTag = this::class.java.simpleName
    Log.e(logTag, message, e)
}

fun Any.logD(message: String) {
    val logTag = this::class.java.simpleName
    Log.d(logTag, message)
}

fun Atom.Coordinate.toVec3() =
    Vector3(x, y, z)

fun String.splitOnlyWords(): List<String> =
    split(" ").filter { string ->
        string != ""
    }
//4fw, 15p, b12, dw2, lz0, pc1, pve, rmd