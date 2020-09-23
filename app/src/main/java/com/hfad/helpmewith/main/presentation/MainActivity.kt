package com.hfad.helpmewith.main.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.hfad.helpmewith.R
import com.hfad.helpmewith.main.meetings.presentation.MeetingsFragment
import com.hfad.helpmewith.main.offers.presentation.OffersFragment
import com.hfad.helpmewith.main.presentation.factory.MainFragmentFactory
import com.hfad.helpmewith.main.profile.presentation.ProfileFragment
import com.hfad.helpmewith.main.search.data.model.SearchModel
import com.hfad.helpmewith.main.search.presentation.SearchFragment
import com.hfad.helpmewith.main.search.presentation.SearchTutorsActivity
import com.hfad.helpmewith.start.StartActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ProfileFragment.LoggedOut, SearchFragment.IOnSearch {

    @Inject
    lateinit var fragmentFactory: MainFragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.fragmentFactory = fragmentFactory
        setListener()
        val fragment = supportFragmentManager.findFragmentById(container_main.id)
        if (fragment == null) {
            bnv_main.selectedItemId = R.id.page_profile
        }
    }

    private fun setListener() {
        bnv_main.setOnNavigationItemSelectedListener { item: MenuItem ->
            return@setOnNavigationItemSelectedListener when (item.itemId) {
                R.id.page_meetings -> {
                    openFragment(MeetingsFragment.newInstance(), "MEETINGS")
                    true
                }
                R.id.page_search -> {
                    openFragment(SearchFragment.newInstance(), "SEARCH")
                    true
                }
                R.id.page_offers -> {
                    openFragment(OffersFragment.newInstance(), "OFFERS")
                    true
                }
                R.id.page_profile -> {
                    openFragment(ProfileFragment.newInstance(), "PROFILE")
                    true
                }
                else -> false
            }
        }
        bnv_main.setOnNavigationItemReselectedListener {}
    }

    private fun openFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.commit {
            replace(container_main.id, fragment, tag)
        }
    }

    companion object {

        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

    override fun logOut() {
        StartActivity.start(this)
    }

    override fun onSearch(searchModel: SearchModel) {
        SearchTutorsActivity.start(this, searchModel)
    }
}
