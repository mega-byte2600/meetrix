package com.starrepublic.meetrix.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.api.client.extensions.android.json.AndroidJsonFactory
import com.google.api.client.json.JsonFactory
import com.google.api.services.admin.directory.model.CalendarResource

/**
 * Created by richard on 2016-11-05.
 */
class Settings(val context: Context) {
    private val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    private val editor: SharedPreferences.Editor

    companion object {
        var JSON_FACTORY: JsonFactory = AndroidJsonFactory()
    }

    init {
        editor = preferences.edit()
    }

    var accountName: String?
        get() = preferences.getString(Settings::accountName.name, null)
        set(value) {
            editor.putString(Settings::accountName.name, value).commit()
        }
    var roomResourceId: CalendarResource?
        get() = decode<CalendarResource>(Settings::roomResourceId.name)
        set(value) = encode(Settings::roomResourceId.name, value)

    private fun encode(key: String, value: Any?) {
        editor.putString(key, JSON_FACTORY.toString(value)).commit()
    }

    inline private fun <reified T : Any> decode(name: String): T? {
        val data = preferences.getString(name, null) ?: return null
        return JSON_FACTORY.fromString(data, T::class.java)
    }
}
