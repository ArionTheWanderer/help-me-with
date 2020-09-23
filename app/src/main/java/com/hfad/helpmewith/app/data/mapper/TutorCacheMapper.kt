package com.hfad.helpmewith.app.data.mapper

import com.hfad.helpmewith.app.data.db.model.Review
import com.hfad.helpmewith.app.data.db.model.TutorInfo
import com.hfad.helpmewith.app.data.db.model.TutorsSubject
import com.hfad.helpmewith.app.data.db.model.User
import com.hfad.helpmewith.app.data.model.*
import com.hfad.helpmewith.util.EntityMapper
import javax.inject.Inject

class TutorCacheMapper
@Inject
constructor(): EntityMapper<User, UserWrapperModel> {

    override fun mapFromEntity(entity: User): UserWrapperModel {
        val tutorsSubjects = entity.tutorsSubjects
        var tutorsSubjectsModel: List<TutorsSubjectModel>? = null
        if (tutorsSubjects != null && tutorsSubjects.isNotEmpty()) {
            tutorsSubjectsModel = mutableListOf()
            for (i in tutorsSubjects.indices) {
                val subject = tutorsSubjects[i].subject
                val experience = tutorsSubjects[i].experience
                val hourlyFee = tutorsSubjects[i].hourlyFee
                tutorsSubjectsModel.add(TutorsSubjectModel(subject, experience, hourlyFee))
            }
        }
        var reviewsModel: List<ReviewModel>? = null
        val reviews = entity.reviews
        if (reviews != null && reviews.isNotEmpty()) {
            reviewsModel = mutableListOf()
            for (i in reviews.indices) {
                val text = reviews[i].text
                val rating = reviews[i].rating
                reviewsModel.add(ReviewModel(text, rating))
            }
        }
        val userInfoModel = UserModel(
            id = entity.id,
            firstName = entity.firstName,
            lastName = entity.lastName,
            login = entity.login,
            isTutor = entity.isTutor,
            rating = entity.rating,
            reviews = reviewsModel
        )
        val tutorInfoModel = TutorInfoModel(
            isNotRemote = entity.tutorInfo?.isNotRemote,
            city = entity.tutorInfo?.city,
            tutorsSubjects = tutorsSubjectsModel
        )
        return UserWrapperModel(
            userInfoModel,
            tutorInfoModel
        )
    }

    override fun mapToEntity(domainModel: UserWrapperModel): User {
        val tutorsSubjectsModel = domainModel.tutorInfo.tutorsSubjects
        var tutorsSubjects: List<TutorsSubject>? = null
        if (tutorsSubjectsModel != null && tutorsSubjectsModel.isNotEmpty()) {
            tutorsSubjects = mutableListOf()
            for (i in tutorsSubjectsModel.indices) {
                val subject = tutorsSubjectsModel[i].subject
                val experience = tutorsSubjectsModel[i].experience
                val hourlyFee = tutorsSubjectsModel[i].hourlyFee
                tutorsSubjects.add(TutorsSubject(subject, experience, hourlyFee))
            }
        }
        val tutorInfo = TutorInfo(
            isNotRemote = domainModel.tutorInfo.isNotRemote,
            city = domainModel.tutorInfo.city
        )
        val reviewsModel = domainModel.userInfo.reviews
        var reviews: List<Review>? = null
        if (reviewsModel != null && reviewsModel.isNotEmpty()) {
            reviews = mutableListOf()
            for (i in reviewsModel.indices) {
                val text = reviewsModel[i].text
                val rating = reviewsModel[i].rating
                reviews.add(Review(text, rating))
            }
        }
        return User(
            id = domainModel.userInfo.id,
            firstName = domainModel.userInfo.firstName,
            lastName = domainModel.userInfo.lastName,
            login = domainModel.userInfo.login,
            isTutor = domainModel.userInfo.isTutor,
            rating = domainModel.userInfo.rating ?: Double.NaN,
            tutorInfo = tutorInfo,
            tutorsSubjects = tutorsSubjects,
            reviews = reviews
        )
    }
}
