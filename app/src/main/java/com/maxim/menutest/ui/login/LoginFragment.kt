package com.maxim.menutest.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.maxim.menutest.R
import com.maxim.menutest.ui.MainActivity
import com.maxim.menutest.util.showError
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.android.ext.android.inject


class LoginFragment: Fragment() {

    private val viewModel: LoginViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_login, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (viewModel.isUserLoggedIn()) navigateToVenues()
        else {
            observeLiveData()
            setOnClickListeners()
        }
    }

    private fun navigateToVenues() {
        activity?.findNavController(R.id.fcvNavContainer)?.navigate(R.id.action_loginFragment_to_venuesFragment)
    }

    private fun observeLiveData() {
        viewModel.ldLoading.observe(viewLifecycleOwner) { show ->
            (activity as MainActivity).run {
                if (show) showLoader()
                else hideLoader()
            }
        }

        viewModel.ldLoginSuccess.observe(viewLifecycleOwner) { token ->
            viewModel.saveUserToken(token)
            navigateToVenues()
        }

        viewModel.ldLoginError.observe(viewLifecycleOwner) { message ->
            activity?.showError(message)
        }
    }

    private fun setOnClickListeners() {
        btnSignIn.setOnClickListener {
            viewModel.loginUser(etEmail.text.toString(), etPassword.text.toString())
        }
    }
}