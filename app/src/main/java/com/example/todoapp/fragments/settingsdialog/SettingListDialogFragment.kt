package com.example.todoapp.fragments.settingsdialog

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.app.appComponent
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
        requireContext().appComponent.settingsDialogComponent().create(this, binding).apply {
            settingsDialogViewController.setOptions()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}