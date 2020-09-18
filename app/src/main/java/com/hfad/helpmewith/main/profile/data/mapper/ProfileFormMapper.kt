package com.hfad.helpmewith.main.profile.data.mapper

import com.hfad.helpmewith.main.profile.data.form.ProfileForm
import com.hfad.helpmewith.main.profile.data.form.ProfileTutorsSubjectForm
import com.hfad.helpmewith.main.profile.data.model.ProfileUserWrapperModel
import com.hfad.helpmewith.util.EntityMapperForm
import javax.inject.Inject

class ProfileFormMapper
@Inject
constructor():
    EntityMapperForm<ProfileForm, ProfileUserWrapperModel> {

    override fun mapToEntity(domainModel: ProfileUserWrapperModel): ProfileForm {
        val subjectsModel = domainModel.tutorInfo?.tutorsSubjects
        var subjectsForm: MutableList<ProfileTutorsSubjectForm>? = null
        if (subjectsModel != null && subjectsModel.isNotEmpty()) {
            subjectsForm = mutableListOf()
            for (i in subjectsModel.indices) {
                val subject = subjectsModel[i].subject
                val experience = subjectsModel[i].experience
                val hourlyFee = subjectsModel[i].hourlyFee
                subjectsForm.add(ProfileTutorsSubjectForm(subject, experience, hourlyFee))
            }
        }
        return ProfileForm(
            firstName = domainModel.userInfo?.firstName,
            lastName = domainModel.userInfo?.lastName,
            oldPassword = domainModel.userInfo?.oldPassword,
            newPassword = domainModel.userInfo?.newPassword,
            isTutor = domainModel.userInfo?.isTutor,
            isNotRemote = domainModel.tutorInfo?.isNotRemote,
            city = domainModel.tutorInfo?.city,
            tutorsSubjects = subjectsForm
        )
    }
}
