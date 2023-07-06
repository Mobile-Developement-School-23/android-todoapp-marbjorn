package com.example.todoapp

import android.content.Context
import android.provider.Settings
import java.util.UUID

class SharedPrefs(context : Context) {
    private val lastRevisionTag = "REVISION"
    private val prefs = context.getSharedPreferences("data", Context.MODE_PRIVATE)
    private var editor = prefs.edit()


    private val deviceId = "DEVICE_ID"
    init {
        setUniqueId()
    }

    private fun setUniqueId() {
        if (!prefs.contains(deviceId)) {
            editor.putString(deviceId, UUID.randomUUID().toString())
            editor.apply()
        }
    }

    //private val deviceID = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
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
}