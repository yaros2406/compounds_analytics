package com.example.swiftyproteins.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.an.biometric.BiometricManager
import com.example.swiftyproteins.presentation.fragments.base.BaseFragment
import com.example.swiftyproteins.presentation.navigation.FromLogin
import com.example.swiftyproteins.presentation.navigation.Screens
import com.example.swiftyproteins.presentation.viewmodels.LoginViewModel
import com.example.swiftyproteins.databinding.FragmentLoginBinding
import com.example.swiftyproteins.presentation.dialog.DialogCreator
import com.example.swiftyproteins.presentation.hide
import com.example.swiftyproteins.presentation.isDeviceLocked
import com.example.swiftyproteins.presentation.models.ProteinError
import com.example.swiftyproteins.presentation.setupPassLock
import com.example.swiftyproteins.presentation.view.LoginBiometricCallback
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment<FromLogin, LoginViewModel>() {

    private var binding: FragmentLoginBinding? = null
    override val viewModel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.btnFingerAuth?.setOnClickListener {
            val isDeviceLocked: Boolean = isDeviceLocked()
            viewModel.onAuthByFingerClick(isDeviceLocked)
        }
        binding?.btnFreeAuth?.setOnClickListener {
            viewModel.onFreeAuthClick()
        }
    }

    override fun handleAction(action: FromLogin) {
        when (action) {
            is FromLogin.Navigate.ProteinList ->
                router.navigateTo(Screens.ProteinList)
            is FromLogin.Command.ShowBiometricAuthDialog ->
                showBiometricAuthDialog()
            is FromLogin.Command.ShowDialogForSetPassLock ->
                showSetLockPassDialog()
            is FromLogin.Command.SetupPassLock ->
                setupPassLock()
            is FromLogin.Command.HideFingerprintButton ->
                binding?.btnFingerAuth?.hide()
            is FromLogin.Command.ShowAuthErrorDialog ->
                showAuthErrorDialog(action.error)
            is FromLogin.Command.ShowToast ->
                Toast.makeText(requireContext(), action.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun showBiometricAuthDialog() {
        val biometricCallback = object : LoginBiometricCallback() {
            override fun onAuthenticationSuccessful() {
                super.onAuthenticationSuccessful()
                viewModel.onAuthSuccess()
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                viewModel.onAuthError(errorCode)
            }
        }
        initBiometricManager(biometricCallback)
    }

    private fun initBiometricManager(biometricCallback: LoginBiometricCallback) =
        try {
            BiometricManager.BiometricBuilder(activity)
                .setTitle("Sing in")
                .setSubtitle("")
                .setDescription("")
                .setNegativeButtonText("Cancel")
                .build()
                .authenticate(biometricCallback)
        } catch (e: Exception) {
            viewModel.onInitBiometricFail()
        }

    private fun showSetLockPassDialog() {
        DialogCreator().showSetLockPassDialog(
            requireContext(), viewModel::onSetupPassLockPositiveClick
        )
    }

    private fun showAuthErrorDialog(error: ProteinError) {
        DialogCreator().showAuthErrorDialog(requireContext(), error)
    }
}