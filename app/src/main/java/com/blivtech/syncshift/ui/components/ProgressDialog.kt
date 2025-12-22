package com.blivtech.syncshift.ui.components

import android.app.Dialog
import android.view.Window
import android.content.Context
import android.widget.TextView
import android.view.WindowManager
import com.blivtech.syncshift.R

class   ProgressDialog(private val context: Context) {
    private val dialog: Dialog = Dialog(context)

    init {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_progress)
        dialog.setCancelable(false)


        val textView: TextView = dialog.findViewById(R.id.tv_label_progress)
        textView.text = context.getString(R.string.str_loading)
        dialog.window?.attributes?.dimAmount = 0.0f
    }

    fun show(window: Window) {
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        if (!dialog.isShowing) {
            dialog.show()
        }
    }

    fun dismiss(window: Window) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        if (dialog.isShowing) {
            dialog.dismiss()
        }
    }
}