package com.hfad.helpmewith.main.search.presentation

import androidx.fragment.app.Fragment
import com.hfad.helpmewith.R
import kotlinx.android.synthetic.main.fragment_search_city_info.*

class SearchCityInfoFragment : Fragment(R.layout.fragment_search_city_info), SearchFragment.ICityInfoGetter {

    override fun getCityInfo(): String {
        return spinner_search_city.selectedItem.toString()
    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchCityInfoFragment()
    }
}
