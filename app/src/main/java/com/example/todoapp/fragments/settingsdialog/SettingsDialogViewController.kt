package com.example.todoapp.fragments.settingsdialog

import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.data.SharedPrefs
import com.example.todoapp.databinding.FragmentSettingListDialogListDialogBinding
import javax.inject.Inject

class SettingsDialogViewController @Inject constructor(
    val fragment: SettingListDialogFragment,
    val binding: FragmentSettingListDialogListDialogBinding,
    val prefs : SharedPrefs
) {

    fun setOptions() {
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

    private fun changeTheme(theme : SystemTheme) {
        val mode  = when (theme) {
            SystemTheme.Light -> AppCompatDelegate.MODE_NIGHT_NO
            SystemTheme.Dark -> AppCompatDelegate.MODE_NIGHT_YES
            else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }
        prefs.setSystemMode(mode)
        AppCompatDelegate.setDefaultNightMode(mode)
        fragment.findNavController().navigate(R.id.action_settingListDialogFragment_to_todoListFragment)
    }
}