package com.hfad.helpmewith.authentication.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE
import androidx.fragment.app.commit
import com.hfad.helpmewith.R
import com.hfad.helpmewith.authentication.presentation.factory.WelcomeFragmentFactory
import com.hfad.helpmewith.main.presentation.MainActivity
import com.hfad.helpmewith.authentication.login.presentation.SignInFragment
import com.hfad.helpmewith.authentication.register.presentation.SignUpFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_welcome.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

private const val WELCOME_FRAGMENT = "WELCOME_FRAGMENT"
private const val SIGN_UP_FRAGMENT = "SIGN_UP_FRAGMENT"
private const val SIGN_IN_FRAGMENT = "sIGN_IN_FRAGMENT"

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class WelcomeActivity : AppCompatActivity(), SignInFragment.SignedIn, SignUpFragment.SignedUp {

    @Inject
    lateinit var fragmentFactory: WelcomeFragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        supportFragmentManager.fragmentFactory = fragmentFactory
        val currentFragment = supportFragmentManager.findFragmentById(fl_welcome.id)
        if (currentFragment == null) {
            supportFragmentManager.commit {
                add(fl_welcome.id, WelcomeFragment.newInstance())
            }
        }
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_welcome_sign_in -> {
                supportFragmentManager.commit {
                    replace(fl_welcome.id, SignInFragment.newInstance())
                    setTransition(TRANSIT_FRAGMENT_FADE)
                }
            }
            R.id.btn_sign_in_back -> {
                supportFragmentManager.commit {
                    replace(fl_welcome.id, WelcomeFragment.newInstance())
                    setTransition(TRANSIT_FRAGMENT_FADE)
                }
            }
            R.id.btn_welcome_sign_up -> {
                supportFragmentManager.commit {
                    replace(fl_welcome.id, SignUpFragment.newInstance())
                    setTransition(TRANSIT_FRAGMENT_FADE)
                }
            }
            R.id.btn_sign_up_back -> {
                supportFragmentManager.commit {
                    setTransition(TRANSIT_FRAGMENT_FADE)
                    replace(fl_welcome.id, WelcomeFragment.newInstance())
                }
            }
        }
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(fl_welcome.id)
        if (fragment is SignInFragment || fragment is SignUpFragment) {
            supportFragmentManager.commit {
                setTransition(TRANSIT_FRAGMENT_FADE)
                replace(fl_welcome.id, WelcomeFragment.newInstance())
            }
        } else {
            super.onBackPressed()
        }
    }

    override fun goToProfile() {
        MainActivity.start(this)
    }

    override fun signedUp() {
        MainActivity.start(this)
    }

    companion object {

        fun start(context: Context) {
            val intent = Intent(context, WelcomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }
}
