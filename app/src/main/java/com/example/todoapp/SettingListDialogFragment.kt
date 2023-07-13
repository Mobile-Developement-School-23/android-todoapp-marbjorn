package com.example.todoapp

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.findNavController
import com.example.todoapp.databinding.FragmentSettingListDialogListDialogBinding


class SettingListDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentSettingListDialogListDialogBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSettingListDialogListDialogBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLight.setOnClickListener {
            changeTheme(SystemTheme.Light)
        }
        binding.btnDark.setOnClickListener {
            changeTheme(SystemTheme.Dark)
        }
        binding.btnSystem.setOnClickListener {
            changeTheme(SystemTheme.System)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun changeTheme(theme : SystemTheme) {
        val mode  = when (theme) {
            SystemTheme.Light -> AppCompatDelegate.MODE_NIGHT_NO
            SystemTheme.Dark -> AppCompatDelegate.MODE_NIGHT_YES
            else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }
        AppCompatDelegate.setDefaultNightMode(mode)
        findNavController().navigate(R.id.action_settingListDialogFragment_to_todoListFragment)
    }
}