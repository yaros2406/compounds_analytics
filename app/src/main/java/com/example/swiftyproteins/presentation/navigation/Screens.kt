package com.example.swiftyproteins.presentation.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.example.swiftyproteins.presentation.fragments.LoginFragment
import com.example.swiftyproteins.presentation.fragments.ProteinFragment
import com.example.swiftyproteins.presentation.fragments.ProteinListFragment
import com.github.terrakok.cicerone.androidx.Creator
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    object ProteinList :
        FragmentScreen(fragmentCreator = object : Creator<FragmentFactory, Fragment> {
            override fun create(argument: FragmentFactory): Fragment =
                ProteinListFragment()
        })

    class Protein(val proteinName: String) :
        FragmentScreen(fragmentCreator = object : Creator<FragmentFactory, Fragment> {
            override fun create(argument: FragmentFactory): Fragment =
                ProteinFragment.newInstance(proteinName)
        })

    object Login : FragmentScreen(fragmentCreator = object : Creator<FragmentFactory, Fragment> {
        override fun create(argument: FragmentFactory): Fragment =
            LoginFragment()
    })
}