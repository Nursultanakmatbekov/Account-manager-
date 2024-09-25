package com.nur.myapplication.ui.fragment.home

import android.accounts.Account
import android.accounts.AccountManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.nur.myapplication.R
import com.nur.myapplication.databinding.FragmentHomeBinding
import com.nur.myapplication.ui.adapter.AnimeAdapter
import com.nur.myapplication.ui.fragment.home.state.HomeIntent
import com.nur.myapplication.ui.fragment.home.state.HomeState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    @Inject
    lateinit var accountManager: AccountManager

    private val viewModel: HomeViewModel by viewModels()
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val animeAdapter = AnimeAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        subscribeToAnime()
        viewModel.send(HomeIntent.LoadAnime())
        setOnClickListeners()
    }

    private fun initialize() {
        binding.recyclerView.adapter = animeAdapter
    }

    private fun subscribeToAnime() = with(binding) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    when (state) {
                        is HomeState.Idle -> {}
                        is HomeState.Loading -> {
                        }

                        is HomeState.Success -> {
                            animeAdapter.submitList(state.anime)
                        }

                        is HomeState.Error -> {
                            Toast.makeText(requireContext(), state.error, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    private fun setOnClickListeners() {
        binding.idBtn.setOnClickListener {
            val accounts: Array<Account> = accountManager.getAccountsByType("My Application")
            val tokenMessage = if (accounts.isNotEmpty()) {
                val token = accountManager.peekAuthToken(accounts[0], "full_access")
                if (!token.isNullOrEmpty()) {
                    "Токен доступен: $token"
                } else {
                    "Токен не найден, требуется аутентификация."
                }
            } else {
                "Аккаунт не найден, требуется аутентификация."
            }
            Toast.makeText(requireContext(), tokenMessage, Toast.LENGTH_SHORT).show()
        }
    }
}