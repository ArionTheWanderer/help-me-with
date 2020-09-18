package com.hfad.helpmewith.app.data.mapper

import com.hfad.helpmewith.app.data.model.*
import com.hfad.helpmewith.app.data.response.TutorResponse
import com.hfad.helpmewith.util.EntityMapperResponse
import javax.inject.Inject

class TutorResponseMapper
@Inject
constructor(): EntityMapperResponse<TutorResponse, UserWrapperModel> {

    override fun mapFromEntity(entity: TutorResponse): UserWrapperModel {
        val tutorsSubjectResponse = entity.tutorsSubjects
        var tutorsSubjectsModel: MutableList<TutorsSubjectModel>? = null
        if (tutorsSubjectResponse != null && tutorsSubjectResponse.isNotEmpty()) {
            tutorsSubjectsModel = mutableListOf()
            for (i in tutorsSubjectResponse.indices) {
                val subject = tutorsSubjectResponse[i].subject
                val experience = tutorsSubjectResponse[i].experience
                val hourlyFee = tutorsSubjectResponse[i].hourlyFee
                tutorsSubjectsModel.add(TutorsSubjectModel(subject, experience, hourlyFee))
            }
        }
        val tutorInfo = TutorInfoModel(
            isNotRemote = entity.isNotRemote,
            city = entity.city,
            tutorsSubjects = tutorsSubjectsModel
        )

        val reviewsResponse = entity.userInfo.reviews
        var reviewsModel: MutableList<ReviewModel>? = null
        if (reviewsResponse != null && reviewsResponse.isNotEmpty()) {
            reviewsModel = mutableListOf()
            for (i in reviewsResponse.indices) {
                val text = reviewsResponse[i].text
                val rating = reviewsResponse[i].rating
                reviewsModel.add(ReviewModel(text, rating))
            }
        }
        val userInfo = UserModel(
            id = entity.userInfo.id,
            firstName = entity.userInfo.firstName,
            lastName = entity.userInfo.lastName,
            login = entity.userInfo.login,
            isTutor = entity.userInfo.isTutor,
            rating = entity.userInfo.rating,
            reviews = reviewsModel?.toList()
        )

        return UserWrapperModel(
            userInfo = userInfo,
            tutorInfo = tutorInfo
        )
    }
}
