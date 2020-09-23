package com.hfad.helpmewith.main.search.presentation

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.hfad.helpmewith.R
import com.hfad.helpmewith.app.data.model.UserWrapperModel
import com.hfad.helpmewith.util.DataState
import kotlinx.android.synthetic.main.fragment_tutor_subjects_info.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class TutorSubjectsInfoFragment : Fragment(R.layout.fragment_tutor_subjects_info) {

    private val viewModel: TutorActivityViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Success<UserWrapperModel> -> {
                    tv_tutor_is_not_remote.text = it.data.tutorInfo.isNotRemote.toString()
                    if (it.data.tutorInfo.city != null) {
                        tv_tutor_city.text = it.data.tutorInfo.city
                    } else {
                        tv_tutor_city.visibility = View.GONE
                    }
                    val tutorSubjects = it.data.tutorInfo.tutorsSubjects
                    if (tutorSubjects != null && tutorSubjects.isNotEmpty()) {
                        for (i in tutorSubjects.indices) {
                            val subject = layoutInflater.inflate(R.layout.item_tutor_subject, null)
                            val subjectName = subject.findViewById<TextView>(R.id.tv_tutor_subject)
                            val experience = subject.findViewById<TextView>(R.id.tv_tutor_experience)
                            val hourlyFee = subject.findViewById<TextView>(R.id.tv_tutor_hourly_fee)
                            subjectName.text = tutorSubjects[i].subject
                            experience.text = tutorSubjects[i].experience.toString()
                            hourlyFee.text = tutorSubjects[i].hourlyFee.toString()
                            ll_tutor_subjects.addView(subject)
                        }
                    }
                }
                is DataState.Error -> {

                }
                else -> {

                }
            }
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() = TutorSubjectsInfoFragment()
    }
}
