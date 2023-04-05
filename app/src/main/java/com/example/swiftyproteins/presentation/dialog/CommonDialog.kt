package com.example.swiftyproteins.presentation.dialog

import android.app.Dialog
import android.content.Context
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog

class CommonDialog {
    private var currentDialog: Dialog? = null

    private var functionAfterPositiveClick: (() -> Unit)? = null
    private var functionAfterNegativeClick: (() -> Unit)? = null
    private var title: String? = null
    private var positiveButton: Int? = null
    private var negativeButton: Int? = null
    private var message: Int? = null

    fun setTitle(newTitle: String?): CommonDialog {
        title = newTitle
        return this
    }

    fun setFunctionOnPositive(function: () -> Unit): CommonDialog {
        functionAfterPositiveClick = function
        return this
    }

    fun setFunctionOnNegative(function: (() -> Unit)?): CommonDialog {
        functionAfterNegativeClick = function
        return this
    }

    fun setPositiveButtonTitle(@StringRes titleId: Int): CommonDialog {
        positiveButton = titleId
        return this
    }

    fun setMessage(@StringRes messageId: Int): CommonDialog {
        message = messageId
        return this
    }

    fun setNegativeButtonTitle(@StringRes titleId: Int): CommonDialog {
        negativeButton = titleId
        return this
    }

    fun showDialog(context: Context): CommonDialog {
        currentDialog = context.let {
            val builderDialog = AlertDialog.Builder(it)
                .setCancelable(true)
            title?.let { title ->
                builderDialog?.setTitle(title)
            }
            message?.let { message ->
                builderDialog?.setMessage(message)
            }
            positiveButton?.let { buttonTitle ->
                builderDialog?.setPositiveButton(buttonTitle) { _, _ ->
                    functionAfterPositiveClick?.invoke()
                }
            }
            negativeButton?.let { negativeButton ->
                builderDialog?.setNegativeButton(negativeButton) { _, _ ->
                    functionAfterNegativeClick?.invoke()
                }
            }
            builderDialog?.show()
        }
        return this
    }

    fun onDestroy() =
        currentDialog?.dismiss()
}