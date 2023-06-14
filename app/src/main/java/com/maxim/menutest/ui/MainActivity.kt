package com.maxim.menutest.ui

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import com.maxim.menutest.R

class MainActivity : AppCompatActivity() {

    lateinit var loader: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_main)
    }


    /**
     * Both [showLoader] and [hideLoader] should ideally be in some kind of
     * Base Fragment/Activity or some Singleton, but for simplicity reasons
     * I won't create those classes, rather put them here so we can show/hide on any Fragment
     */
    fun showLoader() {
        loader = AlertDialog.Builder(this).apply {
            setView(R.layout.dialog_loading)
            create().apply {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
            }

        }.show()
        loader.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    fun hideLoader() {
        loader.hide()
    }
}