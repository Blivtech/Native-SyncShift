    package com.blivtech.syncshift.utils

    import android.annotation.SuppressLint
    import android.content.Context
    import android.content.SharedPreferences
    import com.blivtech.syncshift.data.model.response.LoginResponse

    @SuppressLint("CommitPrefEdits")
    object SharedPreferencesManager {

        // Shared Preference File Name
        private const val PREF_NAME = "SyncShift"

        // Keys
        private const val LOGIN_STATUS = "LogInstatus"
        private const val ACTIVE_COMPANY_NAME = "ActiveCompanyName"
        private const val ACTIVE_COMPANY_CODE = "ActiveCompanyCode"
        private const val ACTIVE_COMPANY_INDUSTRY = "ActiveCompanyIndustryName"

        private const val BT_CODE = "bt_code"
        private const val USERNAME = "username"
        private const val NAME = "name"
        private const val MOBILE = "mobile_number"
        private const val USER_TYPE = "usertype"
        private const val ACTIVE_DATE = "active_date"

        // ---------------------------------------
        // SharedPreference Instance
        // ---------------------------------------

        private fun getPrefs(context: Context): SharedPreferences {
            return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        }

        private fun editor(context: Context): SharedPreferences.Editor {
            return getPrefs(context).edit()
        }

        // ---------------------------------------
        // Login Status
        // ---------------------------------------

        fun setLoginStatus(context: Context, status: Boolean) {
            editor(context).putBoolean(LOGIN_STATUS, status).apply()
        }

        fun getLoginStatus(context: Context): Boolean {
            return getPrefs(context).getBoolean(LOGIN_STATUS, false)
        }

        // ---------------------------------------
        // Insert Login Data
        // ---------------------------------------

        fun insertLoginData(context: Context, data: LoginResponse) {
            editor(context).apply {
                putString(BT_CODE, data.btCode)
                putString(USERNAME, data.userName)
                putString(NAME, data.btName)
                putString(MOBILE, data.mobileNumber)
                putString(USER_TYPE, data.userType)
                putString(ACTIVE_DATE, "")
                putBoolean(LOGIN_STATUS, true)
                apply()
            }
        }

        // ---------------------------------------
        // Get Login Data
        // ---------------------------------------

        fun getLoginData(context: Context): LoginResponse {

            val pref = getPrefs(context)

            return LoginResponse(
                btCode = pref.getString(BT_CODE, "") ?: "",
                userName = pref.getString(USERNAME, "") ?: "",
                btName = pref.getString(NAME, "") ?: "",
                mobileNumber = pref.getString(MOBILE, "") ?: "",
                userType = pref.getString(USER_TYPE, "") ?: ""
            )
        }

        // ---------------------------------------
        // Active Company Details
        // ---------------------------------------

        fun setActiveCompanyName(context: Context, value: String) {
            editor(context).putString(ACTIVE_COMPANY_NAME, value).apply()
        }

        fun getActiveCompanyName(context: Context): String {
            return getPrefs(context).getString(ACTIVE_COMPANY_NAME, "") ?: ""
        }

        fun setActiveCompanyCode(context: Context, value: String) {
            editor(context).putString(ACTIVE_COMPANY_CODE, value).apply()
        }

        fun getActiveCompanyCode(context: Context): String {
            return getPrefs(context).getString(ACTIVE_COMPANY_CODE, "") ?: ""
        }

        fun setActiveCompanyIndustryName(context: Context, value: String) {
            editor(context).putString(ACTIVE_COMPANY_INDUSTRY, value).apply()
        }

        fun getActiveCompanyIndustryName(context: Context): String {
            return getPrefs(context).getString(ACTIVE_COMPANY_INDUSTRY, "") ?: ""
        }
    }