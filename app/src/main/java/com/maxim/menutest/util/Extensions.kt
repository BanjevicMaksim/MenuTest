package com.maxim.menutest.util

import android.app.Activity
import android.app.AlertDialog
import android.view.Window
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
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