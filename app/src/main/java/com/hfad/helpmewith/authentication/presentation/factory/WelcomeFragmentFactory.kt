package com.hfad.helpmewith.authentication.presentation.factory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.hfad.helpmewith.authentication.login.presentation.SignInFragment
import com.hfad.helpmewith.authentication.presentation.WelcomeFragment
import com.hfad.helpmewith.authentication.register.presentation.SignUpFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class WelcomeFragmentFactory
@Inject
constructor(): FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {

            WelcomeFragment::class.java.name -> {
                WelcomeFragment.newInstance()
            }

            SignInFragment::class.java.name -> {
                SignInFragment.newInstance()
            }

            SignUpFragment::class.java.name -> {
                SignUpFragment.newInstance()
            }

            else -> super.instantiate(classLoader, className)
        }
    }
}
