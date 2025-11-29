package com.example.util

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager private constructor(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    var authToken: String?
        get() = prefs.getString("auth_token", null)
        set(value) = prefs.edit().putString("auth_token", value).apply()

    var lastSyncIso: String?
        get() = prefs.getString("last_sync_iso", null)
        set(value) = prefs.edit().putString("last_sync_iso", value).apply()

    fun clearAll() {
        prefs.edit().clear().apply()
    }

    companion object {
        @Volatile private var INSTANCE: PreferencesManager? = null
        fun getInstance(context: Context): PreferencesManager {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: PreferencesManager(context.applicationContext).also { INSTANCE = it }
            }
        }
    }
}
