package com.hfad.helpmewith.main.presentation.factory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.hfad.helpmewith.main.meetings.presentation.MeetingsFragment
import com.hfad.helpmewith.main.offers.presentation.OffersFragment
import com.hfad.helpmewith.main.profile.presentation.ProfileFragment
import com.hfad.helpmewith.main.search.presentation.SearchFragment
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

            MeetingsFragment::class.java.name -> {
                MeetingsFragment.newInstance()
            }

            OffersFragment::class.java.name -> {
                OffersFragment.newInstance()
            }

            SearchFragment::class.java.name -> {
                SearchFragment.newInstance()
            }

            else -> super.instantiate(classLoader, className)
        }
    }
}
