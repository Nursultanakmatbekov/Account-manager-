package com.nur.myapplication.ui.fragment.blank

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.nur.myapplication.R
import com.nur.myapplication.databinding.FragmentBlankBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Фрагмент для демонстрации различных пользовательских интерфейсов и взаимодействий.
 *
 * Этот фрагмент предназначен для UI тестов.
 * Он включает в себя кнопки для отображения Snackbar, изменения текста
 * и навигации к другому фрагменту.
 */
@AndroidEntryPoint
class BlankFragment : Fragment() {

    private val binding by viewBinding(FragmentBlankBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Инфляция макета для этого фрагмента
        return inflater.inflate(R.layout.fragment_blank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListeners() // Установка слушателей нажатий кнопок
    }

    /**
     * Установка слушателей нажатий для кнопок в пользовательском интерфейсе.
     */
    private fun setOnClickListeners() {
        binding.btnSnackbar.setOnClickListener {
            binding.tvOk.text = "OK"
            Snackbar.make(binding.root, "OK", Snackbar.LENGTH_LONG).show() // Отображение Snackbar
        }
        binding.btnChangeText.setOnClickListener {
            binding.tvText.text = "Hello" // Изменение текста в TextView
        }
        binding.btnNavigation.setOnClickListener {
            findNavController().navigate(R.id.action_blankFragment_to_homeFragment) // Навигация к домашнему фрагменту
        }
    }
}
