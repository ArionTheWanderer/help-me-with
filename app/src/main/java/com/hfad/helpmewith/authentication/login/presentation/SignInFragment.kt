package com.hfad.helpmewith.authentication.login.presentation

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.hfad.helpmewith.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_welcome.*
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancelChildren
import java.util.zip.Inflater

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_sign_in), CoroutineScope by MainScope() {

    private val signInViewModel: SignInViewModel by viewModels()
    private var listener: SignedIn? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = activity as? SignedIn
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservers()
        btn_sign_in_sign_in.setOnClickListener {
            val login = et_sign_in_login.text.toString()
            val password = et_sign_in_pass.text.toString()
            signInViewModel.postLogin(login, password)
        }
    }

    private fun subscribeObservers() {
        signInViewModel.isAuthenticated.observe(viewLifecycleOwner) { result ->
            when(result) {
                true -> {
                    listener?.goToProfile()
                    // Toast.makeText(context, "YES YES YES!!!", Toast.LENGTH_SHORT).show()
                }
                else -> Snackbar.make(requireActivity().findViewById(R.id.welcome_parent), getString(R.string.snackbar_sign_in_error_bad_request), Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineContext.cancelChildren()
    }

    interface SignedIn {
        fun goToProfile()
    }

    companion object {
        @JvmStatic
        fun newInstance() = SignInFragment()
    }
}