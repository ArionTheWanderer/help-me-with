package com.hfad.helpmewith.authentication.register.data.mapper


import com.hfad.helpmewith.authentication.register.data.form.SignUpForm
import com.hfad.helpmewith.authentication.register.data.form.SignUpTutorsSubjectForm
import com.hfad.helpmewith.authentication.register.data.model.SignUpModel
import com.hfad.helpmewith.authentication.register.data.model.SignUpTutorsInfoModel
import com.hfad.helpmewith.authentication.register.data.model.SignUpTutorsSubjectModel
import com.hfad.helpmewith.util.EntityMapper
import com.hfad.helpmewith.util.EntityMapperForm
import javax.inject.Inject

class SignUpMapper
@Inject
constructor():
    EntityMapperForm<SignUpForm, SignUpModel> {

    /*override fun mapFromEntity(entity: SignUpForm): SignUpModel {
        val subjectsForm = entity.tutorsSubjects
        var subjectsModel: MutableList<SignUpTutorsSubjectModel>? = null
        if (subjectsForm != null && subjectsForm.size != 0) {
            subjectsModel = mutableListOf()
            for (i in 0 until subjectsForm.size) {
                val subject = subjectsForm[i].subject
                val experience = subjectsForm[i].experience
                val hourlyFee = subjectsForm[i].hourlyFee
                subjectsModel.add(SignUpTutorsSubjectModel(subject, experience, hourlyFee))
            }
        }
        return SignUpModel(
            firstName = entity.firstName,
            lastName = entity.lastName,
            login = entity.login,
            password = entity.password,
            isTutor = entity.isTutor,
            signUpTutorsInfoModel = SignUpTutorsInfoModel(entity.isNotRemote, entity.city, subjectsModel)
        )
    }*/

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
