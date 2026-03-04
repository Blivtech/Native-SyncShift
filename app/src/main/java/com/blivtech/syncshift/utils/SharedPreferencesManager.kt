package com.blivtech.syncshift.utils

import android.content.Context
import android.annotation.SuppressLint
import com.blivtech.syncshift.data.model.response.LoginResponse

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
    fun insertLoginData(context: Context, data: LoginResponse) {
        val sp = context.getSharedPreferences(SyncShift, Context.MODE_PRIVATE)
        val editor = sp.edit()

        editor.putString("bt_code", data.btcode)
        editor.putString("username", data.username)
        editor.putString("name", data.btname)
        editor.putString("mobile_number", data.mobileNumber)
        editor.putString("usertype", data.usertype)
        editor.putString("active_date", "")

        editor.putBoolean(LogInstatus, true)

        editor.apply()
    }


    fun getLoginData(context: Context): LoginResponse {
        val sharedPreferences = context.getSharedPreferences(SyncShift, Context.MODE_PRIVATE)

        val btCode = sharedPreferences.getString("bt_code", "") ?:""

        return LoginResponse(
            btcode = btCode,
            username = sharedPreferences.getString("username", "") ?: "",
            btname = sharedPreferences.getString("name", "") ?: "",
            mobileNumber = sharedPreferences.getString("mobile_number", "") ?: "",
            usertype = sharedPreferences.getString("usertype", "") ?: "",

            )
    }

}