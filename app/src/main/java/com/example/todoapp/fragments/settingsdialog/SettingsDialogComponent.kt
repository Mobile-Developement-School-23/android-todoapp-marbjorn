package com.example.todoapp.fragments.settingsdialog

import com.example.todoapp.data.SharedPrefs
import com.example.todoapp.databinding.FragmentAddTaskBinding
import com.example.todoapp.databinding.FragmentSettingListDialogListDialogBinding
import dagger.BindsInstance
import dagger.Subcomponent
import javax.inject.Scope


@Scope
annotation class SettingsScope

@SettingsScope
@Subcomponent
interface SettingsDialogComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance fragment: SettingListDialogFragment,
            @BindsInstance binding: FragmentSettingListDialogListDialogBinding
        ) : SettingsDialogComponent
    }

    val settingsDialogViewController : SettingsDialogViewController

}