package com.maxim.menutest.util

import android.app.AlertDialog
import android.view.Window
import androidx.fragment.app.Fragment

fun Fragment.showError(message: InfoMessage) {
    AlertDialog.Builder(activity).apply {
        setMessage(message.body)
        setTitle(message.title)
        setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        create().apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
        }
        show()
    }
}