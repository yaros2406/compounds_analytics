package com.example.swiftyproteins.presentation.dialog

import android.content.Context
import androidx.annotation.StringRes
import com.example.swiftyproteins.R
import com.example.swiftyproteins.presentation.models.ProteinError

class DialogCreator {

    fun showNotFoundErrorDialog(
        context: Context,
        error: ProteinError,
        onButtonClick: () -> Unit
    ): CommonDialog =
        CommonDialog()
            .setTitle(context.getString(error.getTitleResId()))
            .setMessage(error.getErrorMessageResId())
            .setPositiveButtonTitle(R.string.ok)
            .setFunctionOnPositive(onButtonClick)
            .showDialog(context)

    fun showSetLockPassDialog(
        context: Context,
        onPositiveClick: () -> Unit
    ): CommonDialog =
        showCancelableAndContinueDialog(
            context = context,
            title = context.getString(R.string.warning),
            messageResId = R.string.message_setup_pass_lock,
            onCancelClick = null,
            onContinueClick = onPositiveClick
        )

    fun showAuthErrorDialog(
        context: Context,
        error: ProteinError,
    ): CommonDialog =
        showErrorCancelableAndContinueDialog(
            context = context,
            error = error,
            positiveButtonTitleResId = R.string.ok,
            onRetryClick = {}
        )

    fun showNetworkErrorDialog(
        context: Context,
        error: ProteinError,
        onCancelClick: () -> Unit,
        onRetryClick: () -> Unit
    ): CommonDialog =
        showErrorCancelableAndContinueDialog(
            context = context,
            error = error,
            onCancelClick = onCancelClick,
            onRetryClick = onRetryClick
        )

    private fun showErrorCancelableAndContinueDialog(
        context: Context,
        error: ProteinError,
        @StringRes positiveButtonTitleResId: Int = R.string.retry,
        onCancelClick: (() -> Unit)? = null,
        onRetryClick: () -> Unit
    ): CommonDialog =
        showCancelableAndContinueDialog(
            context = context,
            title = context.getString(error.getTitleResId()),
            messageResId = error.getErrorMessageResId(),
            positiveButtonTitleResId = positiveButtonTitleResId,
            onCancelClick = onCancelClick,
            onContinueClick = onRetryClick
        )

    private fun showCancelableAndContinueDialog(
        context: Context,
        title: String,
        @StringRes messageResId: Int,
        @StringRes positiveButtonTitleResId: Int = R.string.__arcore_continue,
        onCancelClick: (() -> Unit)?,
        onContinueClick: () -> Unit
    ): CommonDialog {
       val dialog = CommonDialog()
            .setTitle(title)
            .setMessage(messageResId)
            .setPositiveButtonTitle(positiveButtonTitleResId)
            .setFunctionOnPositive(onContinueClick)
            .setFunctionOnNegative(onCancelClick)
        if (onCancelClick != null) {
            dialog.setNegativeButtonTitle(R.string.cancel)
        }
        dialog.showDialog(context)
        return dialog
    }
}