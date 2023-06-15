package com.maxim.menutest.ui

import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.maxim.menutest.R
import com.maxim.menutest.util.InternetConnectivityManager

class MainActivity : AppCompatActivity() {

    lateinit var loader: AlertDialog
    private lateinit var connectivityManager: InternetConnectivityManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_main)

        setupConnectivityManager()
    }

    /**
     * Whole logic for listening to connectivity changes should ideally be in some kind of
     * Base Activity so that all Activities can show it without repeating the code
     * or some Singleton that has passed app context and can show toast or a Dialog, but for simplicity reasons
     * I won't create those classes, rather put them here so we can observe it and show
     * appropriate Toast
     */
    private fun setupConnectivityManager() {
        connectivityManager = InternetConnectivityManager(this) {
            Toast.makeText(this, "Internet connection ${it.name}", Toast.LENGTH_LONG).show()
        }
        connectivityManager.register()
    }

    override fun onDestroy() {
        super.onDestroy()
        connectivityManager.unregister()
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