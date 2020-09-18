package com.hfad.helpmewith.authentication.register.presentation

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.hfad.helpmewith.R
import com.hfad.helpmewith.authentication.register.data.model.SignUpTutorsInfoModel
import com.hfad.helpmewith.authentication.register.data.model.SignUpTutorsSubjectModel
import kotlinx.android.synthetic.main.fragment_tutor_info.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class SignUpTutorInfoFragment : Fragment(R.layout.fragment_tutor_info),
    SignUpFragment.IGetTutorsInfo {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        spinner_tutor_info_city.isEnabled = false
        spinner_tutor_info_city.isClickable = false
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

    override fun getTutorsInfo(): SignUpTutorsInfoModel? {
        val childCount = ll_sign_up_subjects.childCount
        if (childCount != 0) {

            val subjects: MutableList<SignUpTutorsSubjectModel> = mutableListOf()
            for (i in 0 until ll_sign_up_subjects.childCount) {
                val subject = ll_sign_up_subjects.getChildAt(i) as ViewGroup
                val subjectName = subject.findViewById<Spinner>(R.id.spinner_sign_up_subject)
                val experience = subject.findViewById<EditText>(R.id.experience_sign_up)
                val hourlyFee = subject.findViewById<EditText>(R.id.hourly_fee_sign_up)
                if (subjectName.selectedItem.toString().isNotEmpty() && experience.text.toString().isNotEmpty()
                    && hourlyFee.text.toString().isNotEmpty()) {
                    subjects.add(
                        SignUpTutorsSubjectModel(subjectName.selectedItem.toString(),
                        experience.text.toString().toLong(), hourlyFee.text.toString().toLong())
                    )
                } else {
                    return null
                }
            }
            var isNotRemote = false
            var city: String? = null
            if (cb_tutor_info_add_city.isChecked) {
                isNotRemote = true
                city = spinner_tutor_info_city.selectedItem.toString()
            }
            return SignUpTutorsInfoModel(isNotRemote, city, subjects)
        } else {
            return null
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SignUpTutorInfoFragment()
    }
}
