package com.hfad.helpmewith.main.profile.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hfad.helpmewith.Constants
import com.hfad.helpmewith.R
import com.hfad.helpmewith.app.data.model.UserWrapperModel
import com.hfad.helpmewith.main.profile.data.model.ProfileTutorInfoModel
import com.hfad.helpmewith.main.profile.data.model.ProfileTutorsSubjectModel
import com.hfad.helpmewith.util.DataState
import kotlinx.android.synthetic.main.fragment_tutor_info.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class ProfileTutorInfoFragment : Fragment(R.layout.fragment_tutor_info),
    ProfileFragment.IProfileGetTutorsInfo {

    private val profileViewModel: ProfileViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservers()
        cb_tutor_info_add_city.setOnClickListener {
            if (cb_tutor_info_add_city.isChecked) {
                spinner_tutor_info_city.isEnabled = true
                spinner_tutor_info_city.isClickable = true
            } else {
                spinner_tutor_info_city.isEnabled = false
                spinner_tutor_info_city.isClickable = false
            }
        }
        btn_tutor_info_add_subject.setOnClickListener {
            val subject = layoutInflater.inflate(R.layout.fragment_tutors_subject, null)
            val deleteButton = subject.findViewById<Button>(R.id.btn_tutor_info_delete_subject)
            deleteButton.setOnClickListener {
                ll_sign_up_subjects.removeView(subject)
            }
            ll_sign_up_subjects.addView(subject)
        }
    }

    private fun subscribeObservers() {
        profileViewModel.userWrapperInfo.observe(viewLifecycleOwner) { dataState ->
            when (dataState) {
                is DataState.Success<UserWrapperModel> -> {
                    setContent(dataState.data)
                }
                is DataState.Error -> {

                }
                is DataState.Loading -> {

                }
            }


        }
    }

    private fun setContent(data: UserWrapperModel) {
        cb_tutor_info_add_city.isChecked = data.tutorInfo.isNotRemote ?: false
        if (data.tutorInfo.isNotRemote == true) {
            spinner_tutor_info_city.setSelection(getIndex(spinner_tutor_info_city, data.tutorInfo.city ?: ""))
        } else {
            spinner_tutor_info_city.isEnabled = false
            spinner_tutor_info_city.isClickable = false
        }
        if (data.tutorInfo.tutorsSubjects != null) {
            for (i in data.tutorInfo.tutorsSubjects.indices) {
                val subject = layoutInflater.inflate(R.layout.fragment_tutors_subject, null)
                val subjectName = subject.findViewById<Spinner>(R.id.spinner_sign_up_subject)
                val experience = subject.findViewById<EditText>(R.id.experience_sign_up)
                val hourlyFee = subject.findViewById<EditText>(R.id.hourly_fee_sign_up)
                subjectName.setSelection(getIndex(subjectName, data.tutorInfo.tutorsSubjects[i].subject))
                experience.setText(data.tutorInfo.tutorsSubjects[i].experience.toString())
                hourlyFee.setText(data.tutorInfo.tutorsSubjects[i].hourlyFee.toString())
                val deleteButton = subject.findViewById<Button>(R.id.btn_tutor_info_delete_subject)
                deleteButton.setOnClickListener {
                    ll_sign_up_subjects.removeView(subject)
                }
                ll_sign_up_subjects.addView(subject)
            }
        }
    }

    private fun getIndex(spinner: Spinner, myString: String): Int {
        for (i in 0 until spinner.count) {
            if (spinner.getItemAtPosition(i).toString().equals(myString, ignoreCase = true)) {
                return i
            }
        }
        return 0
    }

    override fun getTutorsInfo(): ProfileTutorInfoModel? {
        val childCount = ll_sign_up_subjects.childCount
        if (childCount != 0) {

            val subjects: MutableList<ProfileTutorsSubjectModel> = mutableListOf()
            for (i in 0 until ll_sign_up_subjects.childCount) {
                val subject = ll_sign_up_subjects.getChildAt(i) as ViewGroup
                val subjectName = subject.findViewById<Spinner>(R.id.spinner_sign_up_subject)
                val experience = subject.findViewById<EditText>(R.id.experience_sign_up)
                val hourlyFee = subject.findViewById<EditText>(R.id.hourly_fee_sign_up)
                if (subjectName.selectedItem.toString().isNotEmpty() && experience.text.toString().isNotEmpty()
                    && hourlyFee.text.toString().isNotEmpty()) {
                    subjects.add(
                        ProfileTutorsSubjectModel(
                            subjectName.selectedItem.toString(),
                            experience.text.toString().toLong(), hourlyFee.text.toString().toLong()
                        )
                    )
                } else {
                    return null
                }
            }
            val checkDistinctSubjectsList = subjects.distinctBy {
                it.subject
            }
            var isNotRemote = false
            var city: String? = null
            if (cb_tutor_info_add_city.isChecked) {
                isNotRemote = true
                city = spinner_tutor_info_city.selectedItem.toString()
            }
            Log.d(Constants.PROFILE_TAG, "checkDistinctSubjectsList.size = ${checkDistinctSubjectsList.size}")
            Log.d(Constants.PROFILE_TAG, "subjects.size = ${subjects.size}")
            if (checkDistinctSubjectsList.size != subjects.size) {
                return ProfileTutorInfoModel(isNotRemote, city, null)
            }
            return ProfileTutorInfoModel(isNotRemote, city, subjects)
        } else {
            return null
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileTutorInfoFragment()
    }
}
