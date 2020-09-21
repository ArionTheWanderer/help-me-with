package com.hfad.helpmewith.main.search.data.mapper

import com.hfad.helpmewith.main.search.data.form.SearchForm
import com.hfad.helpmewith.main.search.data.model.SearchModel
import com.hfad.helpmewith.util.EntityMapperForm
import javax.inject.Inject

class SearchFormMapper
@Inject
constructor():
    EntityMapperForm<SearchForm, SearchModel> {
    override fun mapToEntity(domainModel: SearchModel): SearchForm {
        var mHF = if (domainModel.maxHourlyFee != null && domainModel.maxHourlyFee == "null") {
            null
        } else {
            domainModel.maxHourlyFee?.toLong()
        }
        var c = if (domainModel.city != null && domainModel.city == "null") {
            null
        } else {
            domainModel.city
        }

        return SearchForm(
            subject = domainModel.subject,
            maxHourlyFee = mHF,
            isNotRemote = domainModel.isNotRemote,
            city = c
        )
    }
}
