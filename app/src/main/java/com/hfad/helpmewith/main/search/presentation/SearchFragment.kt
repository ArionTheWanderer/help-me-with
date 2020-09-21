package com.hfad.helpmewith.main.search.presentation

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.commit
import com.hfad.helpmewith.R
import com.hfad.helpmewith.main.search.data.model.SearchModel
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment(R.layout.fragment_search) {

    private lateinit var onSearchClickListener: IOnSearch
    private var cityInfoGetter: ICityInfoGetter? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            onSearchClickListener = activity as IOnSearch
        } catch (e: ClassCastException) {
            throw ClassCastException(
                "$activity must implement IOnSearch"
            )
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cb_search_is_not_remote.setOnClickListener {
            if (cb_search_is_not_remote.isChecked) {
                childFragmentManager.commit {
                    val fragment = SearchCityInfoFragment.newInstance()
                    add(fl_search.id, fragment)
                    onAttachToChildFragment(fragment)
                }
            } else {
                val fragment = childFragmentManager.findFragmentById(fl_search.id)
                if (fragment != null) {
                    childFragmentManager.commit {
                        remove(fragment)
                        cityInfoGetter = null
                    }
                }
            }
        }
        btn_search_search.setOnClickListener {
            search()
        }
    }

    private fun search() {
        val subject = spinner_search_subject.selectedItem.toString()
        var maxHourlyFee: String? = et_search_max_hourly_fee.text.toString()
        if (maxHourlyFee == "") {
            maxHourlyFee = null
        }
        val isNotRemote = cb_search_is_not_remote.isChecked
        var city: String? = ""
        city = if (isNotRemote) {
            cityInfoGetter?.getCityInfo()
        } else {
            null
        }
        onSearchClickListener.onSearch(SearchModel(subject, maxHourlyFee, isNotRemote, city))
    }

    private fun onAttachToChildFragment(fragment: Fragment?) {
        try {
            cityInfoGetter = fragment as ICityInfoGetter
        } catch (e: ClassCastException) {
            throw ClassCastException(
                "$fragment must implement ICityInfoGetter"
            )
        }
    }

    interface IOnSearch {
        fun onSearch(searchModel: SearchModel)
    }

    interface ICityInfoGetter {
        fun getCityInfo(): String
    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment()
    }
}
