package com.hfad.helpmewith.main.presentation.factory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.hfad.helpmewith.main.profile.presentation.ProfileFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MainFragmentFactory
@Inject
constructor(): FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className) {
            ProfileFragment::class.java.name -> {
                ProfileFragment.newInstance()
            }

            else -> super.instantiate(classLoader, className)
        }
    }
}