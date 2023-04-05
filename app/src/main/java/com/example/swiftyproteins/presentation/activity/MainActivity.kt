package com.example.swiftyproteins.presentation.activity

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.core.view.isVisible
import com.example.swiftyproteins.R
import com.example.swiftyproteins.databinding.ActivityMainBinding
import com.example.swiftyproteins.presentation.fragments.base.BaseFragment
import com.example.swiftyproteins.presentation.navigation.Screens
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val navigator by lazy {
        AppNavigator(this, R.id.fragmentContainerView)
    }
    private val router by inject<Router>()
    private val navigatorHolder by inject<NavigatorHolder>()

    private lateinit var binding: ActivityMainBinding
    private val currentFragment: BaseFragment<*, *>?
        get() = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as? BaseFragment<*, *>

    override fun onCreate(savedInstanceState: Bundle?) {
        disableRotationForPhone()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            binding.tvLoading.isVisible = true
        }
        binding.root.postDelayed({ createRootFragment() }, 10000)
    }

    @SuppressLint("SourceLockedOrientationActivity")
    private fun disableRotationForPhone() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigator.let {
            navigatorHolder.setNavigator(it)
        }
    }

    override fun onPause() {
        navigator.let {
            router.newRootScreen(Screens.Login)
            navigatorHolder.removeNavigator()
        }
        super.onPause()
    }

    override fun onBackPressed() {
        when {
            currentFragment?.onBackPressed() == true -> Unit
            else -> super.onBackPressed()
        }
    }

    private fun createRootFragment() {
        binding.tvLoading.isVisible = false
        router.newRootScreen(Screens.Login)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.clear()
        return super.onCreateOptionsMenu(menu)
    }
}