package com.hfad.helpmewith.main.search.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.hfad.helpmewith.R
import com.hfad.helpmewith.app.data.model.UserWrapperModel
import com.hfad.helpmewith.main.search.data.model.SearchModel
import com.hfad.helpmewith.main.search.presentation.recycler.TutorsAdapter
import com.hfad.helpmewith.util.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_search_tutors.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

private const val SUBJECT = "SUBJECT"
private const val MAX_HOURLY_FEE = "MAX_HOURLY_FEE"
private const val IS_NOT_REMOTE = "IS_NOT_REMOTE"
private const val CITY = "CITY"

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SearchTutorsActivity : AppCompatActivity() {

    private val viewModel: SearchTutorsActivityViewModel by viewModels()
    private lateinit var subjectName: String
    private lateinit var maxHourlyFee: String
    private lateinit var isNotRemote: String
    private lateinit var city: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_tutors)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val extras = intent?.extras
        if (extras != null) {
            subjectName = extras.get(SUBJECT).toString()
            maxHourlyFee = extras.get(MAX_HOURLY_FEE).toString()
            isNotRemote = extras.get(IS_NOT_REMOTE).toString()
            city = extras.get(CITY).toString()
        }
        observeViewState(subjectName)
        viewModel.getTutors(SearchModel(subjectName, maxHourlyFee, isNotRemote.toBoolean(), city))
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

    private fun observeViewState(subjectName: String) {
        viewModel.dataState.observe(this,  Observer { state ->
            when(state){
                is DataState.Success<List<UserWrapperModel>> -> {
                    displayProgressBar(false)
                    setRecyclerAdapterData(state.data, subjectName)
                    displayRecyclerView(true)
                }
                is DataState.Error -> {
                    displayProgressBar(false)
                    displayError(state.error)
                }
                is DataState.Loading -> {
                    displayRecyclerView(false)
                    displayProgressBar(true)
                }
            }
        })
    }

    private fun setRecyclerAdapterData(data: List<UserWrapperModel>, subjectName: String) {
        rv_tutors.adapter = TutorsAdapter(data, subjectName, {
            TutorActivity.start(this, it)
        }) {

        }
    }

    private fun displayRecyclerView(isDisplayed: Boolean){
        rv_tutors.visibility = if(isDisplayed) View.VISIBLE else View.GONE
    }

    private fun displayProgressBar(isDisplayed: Boolean){
        pb_tutors.visibility = if(isDisplayed) View.VISIBLE else View.GONE
    }

    private fun displayError(message: String){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    companion object {

        fun start(context: Context, searchModel: SearchModel) {
            val intent = Intent(context, SearchTutorsActivity::class.java)
            intent
                .putExtra(SUBJECT, searchModel.subject)
                .putExtra(MAX_HOURLY_FEE, searchModel.maxHourlyFee)
                .putExtra(IS_NOT_REMOTE, searchModel.isNotRemote)
                .putExtra(CITY, searchModel.city)
            context.startActivity(intent)
        }
    }
}
