package com.example.swiftyproteins.presentation.navigation

import com.example.swiftyproteins.presentation.models.ProteinError

sealed class FromLogin: Action {
    sealed class Navigate: FromLogin() {
        object ProteinList: Navigate()
    }

    sealed class Command: FromLogin() {
        object ShowBiometricAuthDialog : Command()

        object ShowDialogForSetPassLock : Command()

        object SetupPassLock : Command()

        object HideFingerprintButton : Command()

        class ShowAuthErrorDialog(val error: ProteinError): Command()

        class ShowToast(val message: String) : Command()
    }
}