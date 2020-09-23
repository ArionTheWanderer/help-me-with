package com.hfad.helpmewith.main.profile.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.hfad.helpmewith.R
import com.hfad.helpmewith.app.data.model.UserWrapperModel
import com.hfad.helpmewith.main.profile.data.model.ProfileTutorInfoModel
import com.hfad.helpmewith.main.profile.data.model.ProfileUserModel
import com.hfad.helpmewith.main.profile.data.model.ProfileUserWrapperModel
import com.hfad.helpmewith.util.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ProfileFragment: Fragment(R.layout.fragment_profile) {

    private val profileViewModel: ProfileViewModel by viewModels()

    private var listener: LoggedOut? = null
    private var tutorsInfoGetter: IProfileGetTutorsInfo? = null

    private var tutorsInfoModel: ProfileTutorInfoModel? = null
    private var userInfoModel: ProfileUserModel? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = activity as? LoggedOut
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservers()
        btn_profile_logout.setOnClickListener {
            profileViewModel.deleteUserFromDB()
            listener?.logOut()
        }
        cb_profile_tutor.setOnClickListener {
            if (cb_profile_tutor.isChecked) {
                childFragmentManager.commit {
                    val fragment = ProfileTutorInfoFragment.newInstance()
                    add(fl_profile_subjects.id, fragment)
                    onAttachToChildFragment(fragment)
                }
            } else {
                deletePreviousFragment()
            }
        }
        btn_edit_info.setOnClickListener {
            sendFieldsData()
        }
        profileViewModel.getProfile()
    }

    private fun subscribeObservers() {
        profileViewModel.userWrapperInfo.observe(viewLifecycleOwner) { dataState ->
            when (dataState) {
                is DataState.Success<UserWrapperModel> -> {
                    displayProgressBar(false)
                    setContent(dataState.data)
                    displayContent(true)
                }
                is DataState.Error -> {
                    displayProgressBar(false)
                    displayMessage(dataState.error)
                }
                is DataState.Loading -> {
                    displayContent(false)
                    displayProgressBar(true)
                }
            }
        }
    }

    private fun setContent(data: UserWrapperModel) {
        et_profile_first_name.setText(data.userInfo.firstName)
        et_profile_last_name.setText(data.userInfo.lastName)
        tv_profile_login.text = data.userInfo.login
        tv_profile_rating.text = data.userInfo.rating.toString()
        cb_profile_tutor.isChecked = data.userInfo.isTutor
        if (data.userInfo.isTutor) {
            deletePreviousFragment()
            childFragmentManager.commit {
                val fragment = ProfileTutorInfoFragment.newInstance()
                add(fl_profile_subjects.id, fragment)
                onAttachToChildFragment(fragment)
            }
        } else {
            deletePreviousFragment()
        }
    }

    private fun displayProgressBar(isDisplayed: Boolean){
        pb_profile.visibility = if(isDisplayed) View.VISIBLE else View.GONE
    }

    private fun displayContent(isDisplayed: Boolean){
        tv_profile_title.visibility = if(isDisplayed) View.VISIBLE else View.GONE
        cv_profile_info.visibility = if(isDisplayed) View.VISIBLE else View.GONE
    }

    private fun displayMessage(message: String){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    private fun clearPasswordFields() {
        et_profile_old_pass.text.clear()
        et_profile_new_pass.text.clear()
    }

    private fun deletePreviousFragment() {
        val fragmentCandidate = childFragmentManager.findFragmentById(fl_profile_subjects.id)
        if (fragmentCandidate != null) {
            childFragmentManager.commit {
                remove(fragmentCandidate)
                tutorsInfoGetter = null
            }
        }
    }

    private fun sendFieldsData() {
        val firstName = et_profile_first_name.text.toString()
        val lastName = et_profile_last_name.text.toString()
        var oldPassword: String? = et_profile_old_pass.text.toString()
        var newPassword: String? = et_profile_new_pass.text.toString()
        val isTutor = cb_profile_tutor.isChecked
        if (oldPassword?.isNotEmpty() == true || newPassword?.isNotEmpty() == true) {
            if (oldPassword?.isEmpty() == true || newPassword?.isEmpty() == true) {
                Toast.makeText(context, getString(R.string.snackbar_profile_error_pass_empty), Toast.LENGTH_SHORT).show()
                return
            } else if (newPassword != null && newPassword.length < 8) {
                Toast.makeText(context, getString(R.string.snackbar_profile_error_pass_length), Toast.LENGTH_LONG).show()
                return
            } else if (oldPassword.equals(newPassword)) {
                Toast.makeText(context, getString(R.string.snackbar_profile_error_pass_equality), Toast.LENGTH_LONG).show()
            }
        }
        if (cb_profile_tutor.isChecked) {
            val tutorsInfo: ProfileTutorInfoModel? = tutorsInfoGetter?.getTutorsInfo()
            if (tutorsInfo == null) {
                Toast.makeText(context, getString(R.string.snackbar_profile_error_tutor), Toast.LENGTH_SHORT).show()
                return
            } else {
                if (tutorsInfo.tutorsSubjects == null) {
                    Toast.makeText(context, getString(R.string.snackbar_profile_error_tutor_same_subjects), Toast.LENGTH_SHORT).show()
                    return
                }
                tutorsInfoModel = tutorsInfo
            }
        }
        if (firstName.isNotEmpty() && lastName.isNotEmpty()) {
            if (newPassword.equals("") && oldPassword.equals("")) {
                newPassword = null
                oldPassword = null
            }
            userInfoModel = ProfileUserModel(
                firstName = firstName,
                lastName = lastName,
                oldPassword = oldPassword,
                newPassword = newPassword,
                isTutor = isTutor
            )
            val profileUserWrapperModel = ProfileUserWrapperModel(userInfoModel, tutorsInfoModel)
            profileViewModel.editProfile(profileUserWrapperModel)
        } else {
            Toast.makeText(context, getString(R.string.snackbar_profile_error_fill_fields), Toast.LENGTH_SHORT).show()
            return
        }
    }

    private fun onAttachToChildFragment(fragment: Fragment?) {
        try {
            tutorsInfoGetter = fragment as IProfileGetTutorsInfo?
        } catch (e: ClassCastException) {
            throw ClassCastException(
                "$fragment must implement IProfileGetTutorsInfo"
            )
        }
    }

    interface IProfileGetTutorsInfo {
        fun getTutorsInfo(): ProfileTutorInfoModel?
    }

    interface LoggedOut {
        fun logOut()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}
