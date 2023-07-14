package com.example.todoapp.data

import android.app.Application
import android.content.Context
import android.provider.Settings
import com.example.todoapp.fragments.settingsdialog.SystemTheme
import java.util.UUID
import javax.inject.Inject

class SharedPrefs @Inject constructor (application : Application) {
    private val lastRevisionTag = "REVISION"
    private val prefs = application.applicationContext.getSharedPreferences("data", Context.MODE_PRIVATE)
    private var editor = prefs.edit()


    private val deviceId = "DEVICE_ID"

    private val systemMode = "SYSTEM_MODE"
    init {
        setUniqueId()
    }

    private fun setUniqueId() {
        if (!prefs.contains(deviceId)) {
            editor.putString(deviceId, UUID.randomUUID().toString())
            editor.apply()
        }
    }
    fun setRevision(revision : Int) {
        editor.putInt(lastRevisionTag, revision)
        editor.apply()
    }

    fun getRevision() : Int {
        return prefs.getInt(lastRevisionTag, 0)
    }

    fun getDeviceId() : String? {
        return prefs.getString(deviceId, null)
    }

    fun setSystemMode(theme : Int) {
        editor.putInt(systemMode, theme)
        editor.apply()
    }

    fun getSystemMode() : Int {
        return prefs.getInt(systemMode, 0)
    }
}