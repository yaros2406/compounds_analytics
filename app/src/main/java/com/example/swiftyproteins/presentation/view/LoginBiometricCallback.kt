package com.example.swiftyproteins.presentation.view

import com.an.biometric.BiometricCallback
import com.example.swiftyproteins.presentation.logD

open class LoginBiometricCallback: BiometricCallback {
    override fun onSdkVersionNotSupported() {
        logD("Sdk version not supported")
    }

    override fun onBiometricAuthenticationNotSupported() {
        logD("Biometric authentication not supported")
    }

    override fun onBiometricAuthenticationNotAvailable() {
        logD("Biometric authentication not available")
    }

    override fun onBiometricAuthenticationPermissionNotGranted() {
        logD("Biometric authentication permission not granted")
    }

    override fun onBiometricAuthenticationInternalError(error: String) {
        logD("Biometric authentication internal error: $error")
    }

    override fun onAuthenticationFailed() {
        logD("authentication failed")
    }

    override fun onAuthenticationCancelled() {
        logD("authentication cancelled")
    }

    override fun onAuthenticationSuccessful() {
        logD("authentication successful")
    }

    override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence) {
        logD("authentication help: code($helpCode), message($helpString)")
    }

    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
        logD("authentication error: code($errorCode), message($errString)")
    }
}