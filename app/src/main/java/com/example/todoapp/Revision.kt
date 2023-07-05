package com.example.todoapp

import android.content.Context
import android.content.SharedPreferences

class Revision(context : Context) {
    private val LAST_REVISION = "REVISION"
    private val prefs = context.getSharedPreferences("data", Context.MODE_PRIVATE)
    private var editor = prefs.edit()

    fun setRevision(revision : Int) {
        editor.putInt(LAST_REVISION, revision)
        editor.apply()
    }

    fun getRevision() : Int {
        return prefs.getInt(LAST_REVISION, 0)
    }
}