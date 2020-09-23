package com.hfad.helpmewith.authentication.register.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.hfad.helpmewith.R
import com.hfad.helpmewith.authentication.register.data.model.SignUpModel
import com.hfad.helpmewith.authentication.register.data.model.SignUpTutorsInfoModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancelChildren

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SignUpFragment : Fragment(R.layout.fragment_sign_up), CoroutineScope by MainScope() {

    private val signUpViewModel: SignUpViewModel by viewModels()
    private var tutorsInfoGetter: IGetTutorsInfo? = null
    private var listener: SignedUp? = null


    private var tutorsInfoModel: SignUpTutorsInfoModel? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = activity as? SignedUp
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservers()
        cb_tutor.setOnClickListener {
            if (cb_tutor.isChecked) {
                childFragmentManager.commit {
                    val fragment = SignUpTutorInfoFragment.newInstance()
                    add(fl_sign_up.id, fragment)
                    onAttachToChildFragment(fragment)
                }
            } else {
                val fragment = childFragmentManager.findFragmentById(fl_sign_up.id)
                if (fragment != null) {
                    childFragmentManager.commit {
                        remove(fragment)
                        tutorsInfoGetter = null
                    }
                }
            }
        }
        btn_sign_up_sign_up.setOnClickListener {
            sendFieldsData()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineContext.cancelChildren()
    }

    private fun subscribeObservers() {
        signUpViewModel.isAuthenticated.observe(viewLifecycleOwner) { result ->
            when (result) {
                true -> {
                    listener?.signedUp()
                }
                else -> Snackbar.make(requireActivity().findViewById(R.id.welcome_parent), getString(R.string.snackbar_sign_up_error_bad_request), Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendFieldsData() {
        val firstName = et_sign_up_first_name.text.toString()
        val lastName = et_sign_up_last_name.text.toString()
        val login = et_sign_up_login.text.toString()
        val password = et_sign_up_password.text.toString()
        val isTutor = cb_tutor.isChecked
        if (cb_tutor.isChecked) {
            val tutorsInfo: SignUpTutorsInfoModel? = tutorsInfoGetter?.getTutorsInfo()
            if (tutorsInfo == null) {
                Snackbar.make(requireActivity().findViewById(R.id.welcome_parent), getString(R.string.snackbar_sign_up_error_tutor), Snackbar.LENGTH_SHORT).show()
                return
            } else {
                tutorsInfoModel = tutorsInfo
            }
        }
        if (firstName.isNotEmpty() && lastName.isNotEmpty() && login.isNotEmpty()
            && password.isNotEmpty()) {
            if (login.length >= 4 && password.length >= 8) {
                val signUpModel = SignUpModel(firstName, lastName, login, password, isTutor, tutorsInfoModel)
                signUpViewModel.sendSignUpRequest(signUpModel)
            } else {
                Snackbar.make(requireActivity().findViewById(R.id.welcome_parent), getString(R.string.snackbar_sign_up_error_fields_length), Snackbar.LENGTH_LONG).show()
                return
            }
        } else {
            Snackbar.make(requireActivity().findViewById(R.id.welcome_parent), getString(R.string.snackbar_sign_up_error_fill_fields), Snackbar.LENGTH_SHORT).show()
            return
        }
    }

    private fun onAttachToChildFragment(fragment: Fragment?) {
        try {
            tutorsInfoGetter = fragment as IGetTutorsInfo?
        } catch (e: ClassCastException) {
            throw ClassCastException(
                "$fragment must implement IGetTutorsInfo"
            )
        }
    }

    interface IGetTutorsInfo {
        fun getTutorsInfo(): SignUpTutorsInfoModel?
    }

    interface SignedUp {
        fun signedUp()
    }

    companion object {
        @JvmStatic
        fun newInstance() = SignUpFragment()
    }
}
