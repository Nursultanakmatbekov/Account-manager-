package com.nur.myapplication.ui.fragment.singin

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.nur.myapplication.R
import com.nur.myapplication.databinding.FragmentSingInBinding
import com.nur.myapplication.ui.fragment.singin.state.SignInIntent
import com.nur.myapplication.ui.fragment.singin.state.SingInState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SingInFragment : Fragment(R.layout.fragment_sing_in) {

    private val binding: FragmentSingInBinding by viewBinding(FragmentSingInBinding::bind)
    private val viewModel: SingInViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        observeViewModel()
    }

    private fun setupListeners() {
        binding.btmSingin.setOnClickListener {
            if (validateInput()) {
                val email = binding.etEmail.text.toString().trim()
                val password = binding.etPassword.text.toString().trim()
                viewModel.send(SignInIntent.Submit(email, password))
            }
        }
    }

    private fun validateInput(): Boolean {
        var isValid = true

        if (binding.etEmail.text.trim().isEmpty()) {
            binding.etEmail.error = "Введите email"
            isValid = false
        } else {
            binding.etEmail.error = null
        }

        if (binding.etPassword.text.trim().isEmpty()) {
            binding.etPassword.error = "Введите пароль"
            isValid = false
        } else {
            binding.etPassword.error = null
        }

        return isValid
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.signInState.collect { state ->
                when (state) {
                    is SingInState.Idle -> {
                        hideProgressBar()
                    }

                    is SingInState.Loading -> {
                        showProgressBar()
                    }

                    is SingInState.Success -> {
                        hideProgressBar()
                        findNavController().navigate(R.id.action_singInFragment_to_homeFragment)
                    }

                    is SingInState.Error -> {
                        hideProgressBar()
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }
}
