package com.maxim.menutest.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.maxim.menutest.R
import org.koin.android.ext.android.inject

class LoginFragment: Fragment() {

    private val viewModel: LoginViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeLiveData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    private fun observeLiveData() {
        viewModel.ldLoading.observe(viewLifecycleOwner) { show ->
            // Show loader
        }

        viewModel.ldLoginSuccess.observe(viewLifecycleOwner) { token ->
            viewModel.saveUserToken(token)
        }

        viewModel.ldLoginError.observe(viewLifecycleOwner) { message ->
            // Show error
        }
    }
}