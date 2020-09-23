package com.hfad.helpmewith.main.search.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.hfad.helpmewith.R
import com.hfad.helpmewith.app.data.model.UserWrapperModel
import com.hfad.helpmewith.util.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_tutor.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

private const val TUTOR_ID = "TUTOR_ID"

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class TutorActivity : AppCompatActivity() {

    private val viewModel: TutorActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutor)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val extras = intent?.extras
        val tutorId = extras?.getLong(TUTOR_ID) ?: 1
        subscribeObservers()
        viewModel.findTutor(tutorId)
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(this) {
            when (it) {
                is DataState.Success<UserWrapperModel> -> {
                    displayProgressBar(false)
                    setTextFields(it.data)
                    displayTitle(true)
                    displayCardView(true)
                }
                is DataState.Error -> {
                    displayProgressBar(false)
                }
                is DataState.Loading -> {
                    displayTitle(false)
                    displayCardView(false)
                    displayProgressBar(true)
                }
            }
        }
    }

    private fun setTextFields(data: UserWrapperModel) {
        tv_user_first_name.text = data.userInfo.firstName
        tv_user_last_name.text = data.userInfo.lastName
        tv_user_login.text = data.userInfo.login
        tv_user_rating.text = data.userInfo.rating.toString()
        if (data.userInfo.isTutor) {
            cb_user_tutor.isChecked = true
            supportFragmentManager.commit {
                val fragment = TutorSubjectsInfoFragment.newInstance()
                add(fl_user_subjects.id, fragment)
            }
        }
    }

    private fun displayTitle(isDisplayed: Boolean) {
        tv_user_title.visibility = if(isDisplayed) View.VISIBLE else View.GONE
    }

    private fun displayCardView(isDisplayed: Boolean) {
        cv_user_main.visibility = if(isDisplayed) View.VISIBLE else View.GONE
    }

    private fun displayProgressBar(isDisplayed: Boolean){
        pb_user.visibility = if(isDisplayed) View.VISIBLE else View.GONE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        fun start(context: Context, userWrapperModel: UserWrapperModel) {
            val intent = Intent(context, TutorActivity::class.java)
            intent.putExtra(TUTOR_ID, userWrapperModel.userInfo.id)
            context.startActivity(intent)
        }
    }
}
