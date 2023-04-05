package com.example.swiftyproteins.presentation.viewmodels

import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import androidx.lifecycle.viewModelScope
import com.example.swiftyproteins.presentation.models.ProteinError
import com.example.swiftyproteins.presentation.navigation.FromLogin
import com.example.swiftyproteins.presentation.viewmodels.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel : BaseViewModel<FromLogin>() {
    private var isLoginEnable: Boolean = true

    fun onAuthByFingerClick(isDeviceLocked: Boolean) {
        if (isDeviceLocked) {
            auth()
        } else {
            handleAction(FromLogin.Command.ShowDialogForSetPassLock)
        }
    }

    fun onFreeAuthClick() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            handleAction(FromLogin.Navigate.ProteinList)
        } else {
            handleAction(FromLogin.Command.ShowToast("Unfortunately this application is not supported on this device"))
        }
    }

    private fun auth() {
        if (isLoginEnable) {
            handleAction(FromLogin.Command.ShowBiometricAuthDialog)
        } else {
            handleAction(FromLogin.Command.ShowToast("Too many attempts, please try later"))
        }
    }

    fun onAuthSuccess() =
        handleAction(FromLogin.Navigate.ProteinList)

    fun onAuthError(errorCode: Int) {
        when (errorCode) {
            BiometricPrompt.BIOMETRIC_ERROR_LOCKOUT -> {
                isLoginEnable = false
                viewModelScope.launch(Dispatchers.Main) {
                    delay(MS_IN_HALF_A_MINUTE)
                    isLoginEnable = true
                }
                handleAction(FromLogin.Command.ShowAuthErrorDialog(ProteinError.AuthWarning))
            }
            BiometricPrompt.BIOMETRIC_ERROR_LOCKOUT_PERMANENT ->
                handleAction(FromLogin.Command.ShowAuthErrorDialog(ProteinError.AuthError))
            BiometricPrompt.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                onInitBiometricFail()
            }
            else ->
                handleAction(FromLogin.Command.ShowAuthErrorDialog(ProteinError.UnknownError))
        }
    }

    fun onSetupPassLockPositiveClick() {
        handleAction(FromLogin.Command.SetupPassLock)
    }

    fun onInitBiometricFail() {
        handleAction(FromLogin.Command.ShowToast("Fingerprint authentication on this device is unfortunately not available"))
        viewModelScope.launch {
            delay(1000)
            handleAction(FromLogin.Command.HideFingerprintButton)
        }
    }

    companion object {
        private const val MS_IN_HALF_A_MINUTE = 30000L
    }
}