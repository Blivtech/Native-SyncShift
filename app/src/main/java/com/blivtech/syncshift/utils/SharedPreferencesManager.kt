package com.blivtech.syncshift.utils

import android.content.Context
import android.annotation.SuppressLint
import com.blivtech.syncshift.data.model.response.LoginData
import com.google.gson.JsonObject

@SuppressLint("CommitPrefEdits")
object SharedPreferencesManager {
    //Shared Preference File Name :
    private const val SyncShift = "SyncShift"


    //For Maintain the Login Status (Login Activity) :
    private const val LogInstatus = "LogInstatus"


    /***************************************************************************************************************************************************************************************/
    //Insert login status :
    fun setLoginStatus(context: Context, loginStatus: Boolean) {
        val sharedPreferences = context.getSharedPreferences(SyncShift, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(LogInstatus, loginStatus)
        editor.apply()
    }

    fun getLogInStatus(context: Context): Boolean =
        context.getSharedPreferences(SyncShift, Context.MODE_PRIVATE).getBoolean(LogInstatus, false)



    //Insert login app setup details :
    fun insertLoginData(context: Context, data: LoginData) {
        val sp = context.getSharedPreferences(SyncShift, Context.MODE_PRIVATE)
        val editor = sp.edit()

        editor.putString("bt_code", data.bt_code)
        editor.putString("username", data.username)
        editor.putString("name", data.name)
        editor.putString("mobile_number", data.mobile_number)
        editor.putString("address", data.address)
        editor.putString("category", data.category)
        editor.putString("account_type", data.account_type)
        editor.putString("app_version", data.app_version)
        editor.putString("active_date", data.active_date)

        editor.putBoolean(LogInstatus, true) // Optional: Login status flag

        editor.apply()
    }



    fun getLoginData(context: Context): LoginData {
        val sharedPreferences = context.getSharedPreferences(SyncShift, Context.MODE_PRIVATE)

        val btCode = sharedPreferences.getString("bt_code", "") ?: ""


        return LoginData(
            bt_code = btCode,
            username = sharedPreferences.getString("username", "") ?: "",
            name = sharedPreferences.getString("name", "") ?: "",
            mobile_number = sharedPreferences.getString("mobile_number", "") ?: "",
            address = sharedPreferences.getString("address", "") ?: "",
            category = sharedPreferences.getString("category", "") ?: "",
            account_type = sharedPreferences.getString("account_type", "") ?: "",
            app_version = sharedPreferences.getString("app_version", "") ?: "",
            active_date = sharedPreferences.getString("active_date", "") ?: ""
        )
    }

}