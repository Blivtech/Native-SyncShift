package com.blivtech.syncshift.utils

import android.content.Context
import android.annotation.SuppressLint

@SuppressLint("CommitPrefEdits")
object SharedPreferencesManager {
    //Shared Preference File Name :
    const val SyncShift = "SyncShift"


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

}