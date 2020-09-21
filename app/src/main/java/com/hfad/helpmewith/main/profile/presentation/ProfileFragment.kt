package com.hfad.helpmewith.main.profile.presentation

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.hfad.helpmewith.R
import com.hfad.helpmewith.app.data.model.TutorInfoModel
import com.hfad.helpmewith.authentication.register.data.model.SignUpModel
import com.hfad.helpmewith.main.profile.data.model.ProfileTutorInfoModel
import com.hfad.helpmewith.main.profile.data.model.ProfileUserModel
import com.hfad.helpmewith.main.profile.data.model.ProfileUserWrapperModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancelChildren

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ProfileFragment: Fragment(R.layout.fragment_profile), CoroutineScope by MainScope() {

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
            listener?.logOut()
        }
        cb_profile_tutor.setOnClickListener {
            if (cb_profile_tutor.isChecked) {
                childFragmentManager.commit {
                    val fragment = ProfileTutorInfoFragment.newInstance()
                    add(fl_profile_subjects.id, fragment)
                    onAttachToChildFragment(fragment)
                }
                /*val fragment = childFragmentManager.findFragmentById(fl_sign_up.id)
                onAttachToChildFragment(fragment)*/
            } else {
                deletePreviousFragment()
            }
        }
        btn_edit_info.setOnClickListener {
            sendFieldsData()
        }
        profileViewModel.getProfile()
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineContext.cancelChildren()
    }

    private fun subscribeObservers() {
        profileViewModel.userInfo.observe(viewLifecycleOwner) {
            et_profile_first_name.setText(it.firstName)
            et_profile_last_name.setText(it.lastName)
            tv_profile_login.text = it.login
            tv_profile_rating.text = it.rating.toString()
            cb_profile_tutor.isChecked = it.isTutor
            if (it.isTutor) {
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
        profileViewModel.isCorrectOldPassword.observe(viewLifecycleOwner) {
            when(it) {
                true -> {
                    clearPasswordFields()
                    Toast.makeText(context, getString(R.string.snackbar_profile_ok), Toast.LENGTH_SHORT).show()
                }
                else -> {
                    clearPasswordFields()
                    Toast.makeText(context, getString(R.string.snackbar_profile_error_old_pass_incorrect), Toast.LENGTH_SHORT).show()
                }
            }
        }
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
                // Snackbar.make(requireActivity().findViewById(R.id.main_parent), getString(R.string.snackbar_profile_error_pass_empty), Snackbar.LENGTH_SHORT).show()
                return
            } else if (newPassword != null && newPassword.length < 8) {
                Toast.makeText(context, getString(R.string.snackbar_profile_error_pass_length), Toast.LENGTH_LONG).show()
                // Snackbar.make(requireActivity().findViewById(R.id.main_parent), getString(R.string.snackbar_sign_up_error_pass_length), Snackbar.LENGTH_LONG).show()
                return
            } else if (oldPassword.equals(newPassword)) {
                Toast.makeText(context, getString(R.string.snackbar_profile_error_pass_equality), Toast.LENGTH_LONG).show()
            }
        }
        if (cb_profile_tutor.isChecked) {
            val tutorsInfo: ProfileTutorInfoModel? = tutorsInfoGetter?.getTutorsInfo()
            if (tutorsInfo == null) {
                Toast.makeText(context, getString(R.string.snackbar_profile_error_tutor), Toast.LENGTH_SHORT).show()
                // Snackbar.make(requireActivity().findViewById(R.id.main_parent), getString(R.string.snackbar_profile_error_tutor), Snackbar.LENGTH_SHORT).show()
                return
            } else {
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
            // Snackbar.make(requireActivity().findViewById(R.id.main_parent), getString(R.string.snackbar_profile_error_fill_fields), Snackbar.LENGTH_SHORT).show()
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
