package com.hfad.helpmewith.main.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.hfad.helpmewith.Constants
import com.hfad.helpmewith.R
import com.hfad.helpmewith.main.presentation.factory.MainFragmentFactory
import com.hfad.helpmewith.main.profile.presentation.ProfileFragment
import com.hfad.helpmewith.start.StartActivity
import com.hfad.helpmewith.util.SessionManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ProfileFragment.LoggedOut {

    @Inject
    lateinit var fragmentFactory: MainFragmentFactory

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.fragmentFactory = fragmentFactory
        setListener()
    }

    private fun setListener() {
        bnv_main.setOnNavigationItemSelectedListener { item: MenuItem ->
            return@setOnNavigationItemSelectedListener when (item.itemId) {
                R.id.page_meetings -> {
                    Toast.makeText(this, "Meetings", Toast.LENGTH_SHORT).show()
                    // openFragment(CopyrightFragment.newInstance(), "copyright")
                    true
                }
                R.id.page_search -> {
                    Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show()
                    // openFragment(InvalidsFragment.newInstance(), "invalids")
                    true
                }
                R.id.page_offers -> {
                    Toast.makeText(this, "Offers", Toast.LENGTH_SHORT).show()
                    // openFragment(MemesFragment.newInstance(), "invalids")
                    true
                }
                R.id.page_profile -> {
                    openFragment(ProfileFragment.newInstance(), "PROFILE")
                    true
                }
                else -> false
            }
        }
        bnv_main.selectedItemId = R.id.page_profile
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
        Log.d(Constants.TOKEN_LOG, "Token value: ${sessionManager.fetchAuthToken()}")
        sessionManager.deleteToken()
        Log.d(Constants.TOKEN_LOG, "Token value: ${sessionManager.fetchAuthToken()}")
        StartActivity.start(this)
    }
}