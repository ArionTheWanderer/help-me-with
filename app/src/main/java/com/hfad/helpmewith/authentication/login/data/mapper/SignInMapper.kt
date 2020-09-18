package com.hfad.helpmewith.authentication.login.data.mapper

import com.hfad.helpmewith.authentication.login.data.form.SignInForm
import com.hfad.helpmewith.authentication.login.data.model.SignInModel
import com.hfad.helpmewith.util.EntityMapper
import com.hfad.helpmewith.util.EntityMapperForm
import javax.inject.Inject

class SignInMapper
@Inject
constructor():
    EntityMapperForm<SignInForm, SignInModel> {

    /*override fun mapFromEntity(entity: SignInForm): SignInModel {
        return SignInModel(
            login = entity.login,
            password = entity.password
        )
    }*/

    override fun mapToEntity(domainModel: SignInModel): SignInForm {
        return SignInForm(
            login = domainModel.login,
            password = domainModel.password
        )
    }
}
