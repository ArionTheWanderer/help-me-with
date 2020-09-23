package com.hfad.helpmewith.authentication.register.data.mapper


import com.hfad.helpmewith.authentication.register.data.form.SignUpForm
import com.hfad.helpmewith.authentication.register.data.form.SignUpTutorsSubjectForm
import com.hfad.helpmewith.authentication.register.data.model.SignUpModel
import com.hfad.helpmewith.util.EntityMapperForm
import javax.inject.Inject

class SignUpMapper
@Inject
constructor():
    EntityMapperForm<SignUpForm, SignUpModel> {

    override fun mapToEntity(domainModel: SignUpModel): SignUpForm {
        val subjectsModel = domainModel.signUpTutorsInfoModel?.subjects
        var subjectsForm: MutableList<SignUpTutorsSubjectForm>? = null
        if (subjectsModel != null && subjectsModel.size != 0) {
            subjectsForm = mutableListOf()
            for (i in 0 until subjectsModel.size) {
                val subject = subjectsModel[i].subject
                val experience = subjectsModel[i].experience
                val hourlyFee = subjectsModel[i].hourlyFee
                subjectsForm.add(SignUpTutorsSubjectForm(subject, experience, hourlyFee))
        }
        }
        return SignUpForm(
            firstName = domainModel.firstName,
            lastName = domainModel.lastName,
            login = domainModel.login,
            password = domainModel.password,
            isTutor = domainModel.isTutor,
            isNotRemote = domainModel.signUpTutorsInfoModel?.isNotRemote ?: false,
            city = domainModel.signUpTutorsInfoModel?.city,
            tutorsSubjects = subjectsForm
        )
    }
}
