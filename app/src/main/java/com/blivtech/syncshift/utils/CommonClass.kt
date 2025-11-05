package com.blivtech.syncshift.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast

object CommonClass {

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun launchActivity(context: Context, bundle: Bundle, targetActivity: Class<*>) {
        val intent = Intent(context, targetActivity)
        intent.putExtras(bundle)
        context.startActivity(intent)
    }
    fun launchActivity(context: Context, targetActivity: Class<*>) {
        val intent = Intent(context, targetActivity)
        context.startActivity(intent)
    }
}