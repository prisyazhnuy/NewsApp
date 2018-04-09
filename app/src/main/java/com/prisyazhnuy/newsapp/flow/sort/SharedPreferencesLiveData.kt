package com.prisyazhnuy.newsapp.flow.sort

import android.arch.lifecycle.LiveData
import android.content.SharedPreferences

/**
 * max.pr on 05.04.2018.
 */
abstract class SharedPreferencesLiveData<T>(
        val sharPreferences: SharedPreferences,
        val key: String,
        val defValue: T) : LiveData<T>() {
    private val preferenceChangedListener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
        if (key == this.key) {
            value = getValueFromPreferences(key, defValue)
        }
    }

    abstract fun getValueFromPreferences(key: String, defValue: T): T

    override fun onInactive() {
        sharPreferences.unregisterOnSharedPreferenceChangeListener(preferenceChangedListener)
        super.onInactive()
    }

    override fun onActive() {
        super.onActive()
        value = getValueFromPreferences(key, defValue)
        sharPreferences.registerOnSharedPreferenceChangeListener(preferenceChangedListener)
    }

}

class SharedPreferencesIntLiveData(
        sharPreferences: SharedPreferences,
        key: String,
        defValue: Int) : SharedPreferencesLiveData<Int>(sharPreferences, key, defValue) {
    override fun getValueFromPreferences(key: String, defValue: Int) = sharPreferences.getInt(key, defValue)
}

class SharedPreferencesStringLiveData(
        sharPreferences: SharedPreferences,
        key: String,
        defValue: String) : SharedPreferencesLiveData<String>(sharPreferences, key, defValue) {
    override fun getValueFromPreferences(key: String, defValue: String) = sharPreferences.getString(key, defValue)
}

fun SharedPreferences.intLiveData(key: String, value: Int) = SharedPreferencesIntLiveData(this, key, value)

fun SharedPreferences.stringLiveData(key: String, value: String) = SharedPreferencesStringLiveData(this, key, value)
