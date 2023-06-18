package com.maxim.menutest.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.maxim.menutest.R
import com.maxim.menutest.databinding.FragmentLoginBinding
import com.maxim.menutest.util.hideLoader
import com.maxim.menutest.util.showError
import com.maxim.menutest.util.showLoader
import org.koin.android.ext.android.inject


class LoginFragment: Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
        if (viewModel.isUserLoggedIn()) {
            navigateToVenues()
        } else {
            observeLiveData()
        }
    }

    private fun navigateToVenues() {
        findNavController().navigate(R.id.action_loginFragment_to_venuesFragment)
    }

    private fun observeLiveData() {
        viewModel.ldLoading.observe(viewLifecycleOwner) { show ->
            if (show) showLoader()
            else hideLoader()
        }

        viewModel.ldLoginSuccess.observe(viewLifecycleOwner) { token ->
            navigateToVenues()
        }

        viewModel.ldLoginError.observe(viewLifecycleOwner) { message ->
            activity?.showError(message)
        }
    }

    private fun setOnClickListeners() {
        binding.btnSignIn.setOnClickListener {
            viewModel.loginUser(binding.etEmail.text.toString(), binding.etPassword.text.toString())
        }
    }
}