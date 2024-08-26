/* Copyright (C) 2021 Fststep Next2Me - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
package com.fststep.myshop.core.data

import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.fststep.myshop.registration.model.UserAccount
import com.google.gson.Gson

/**
 * Created by Shubham Singh on 13/05/21.
 */
class Preferences(context: Context) {
    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun storeString(data: String, key: String) {
        preferences.edit {
            putString(key, data)
        }
    }

    fun storeInt(data: Int, key: String) {
        preferences.edit {
            putInt(key, data)
        }
    }

    fun storeBoolean(data: Boolean, key: String) {
        preferences.edit {
            putBoolean(key, data)
        }
    }

    fun storeTempUserAccount(data: UserAccount) {
        storeUserAccount(data, true)
    }

    fun storeUserAccount(data: UserAccount, isTemp: Boolean = false) {
        val mUserAccount = Gson().toJson(data)
        storeString(mUserAccount, if (isTemp) Constants.KEY_TEMP_USER_ACCOUNT else Constants.KEY_USER_ACCOUNT)
    }

    fun fetchString(key: String, defaultValue: String = ""): String {
        return preferences.getString(key, defaultValue) ?: defaultValue
    }

    fun fetchInt(key: String, defaultValue: Int = 0): Int {
        return preferences.getInt(key, defaultValue)
    }

    fun fetchBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return preferences.getBoolean(key, defaultValue)
    }

    fun fetchTempUserAccount(): UserAccount? {
        return fetchUserAccount(true)
    }

    fun fetchUserAccount(isTemp: Boolean = false): UserAccount? {
        val mUserAccount = fetchString(if (isTemp) Constants.KEY_TEMP_USER_ACCOUNT else Constants.KEY_USER_ACCOUNT, "")
        return if (mUserAccount.isNotEmpty()) {
            Gson().fromJson(mUserAccount, UserAccount::class.java)
        } else {
            null
        }
    }

    fun clearAllPreferences() {
        preferences.edit {
            clear()
        }
    }

    fun clearTempUserAccount() {
        clearPreference(Constants.KEY_TEMP_USER_ACCOUNT)
    }

    fun clearPreference(key: String) {
        preferences.edit(commit = true) {
            remove(key)
        }
    }

    fun clearPreferences(keyList: ArrayList<String>) {
        keyList.forEach {
            clearPreference(it)
        }
    }


    fun fetchToken(): String {
        val defaultValue=""
        return preferences.getString(Constants.KEY_LOGIN_TOKEN,defaultValue) ?: defaultValue
    }
    fun storeToken(token:String) {
        storeString(token,Constants.KEY_LOGIN_TOKEN)
    }
    fun fetchUsername(): String {
        val defaultValue=""
        return preferences.getString(Constants.KEY_LOGIN_USERNAME,defaultValue) ?: defaultValue
    }
    fun storeUsername(data:String) {
        storeString(data,Constants.KEY_LOGIN_USERNAME)
    }
    fun fetchPassword(): String {
        val defaultValue=""
        return preferences.getString(Constants.KEY_LOGIN_PASSWORD,defaultValue) ?: defaultValue
    }
    fun storePassword(data:String) {
        storeString(data,Constants.KEY_LOGIN_PASSWORD)
    }

}
