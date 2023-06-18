package com.maxim.menutest.util

import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import androidx.fragment.app.Fragment
import com.maxim.menutest.R

fun Activity.showError(message: InfoMessage) {
    AlertDialog.Builder(this).apply {
        setMessage(message.body)
        setTitle(message.title)
        setPositiveButton(getString(R.string.OK)) { dialog, _ ->
            dialog.dismiss()
        }
        create().apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
        }
        show()
    }
}



lateinit var loader: AlertDialog
fun Fragment.showLoader() {
    loader = AlertDialog.Builder(requireContext()).apply {
        setView(R.layout.dialog_loading)
        create().apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
        }

    }.show()
    loader.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
}

fun Fragment.hideLoader() {
    loader.dismiss()
}