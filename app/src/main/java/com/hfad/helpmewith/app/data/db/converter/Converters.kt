package com.hfad.helpmewith.app.data.db.converter

import androidx.room.TypeConverter
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.hfad.helpmewith.app.data.db.model.Review
import com.hfad.helpmewith.app.data.db.model.TutorsSubject

class Converters {

    private val gson = GsonBuilder().create()

    @TypeConverter
    fun fromTutorsSubjectsToJSON(tutorsSubjects: List<TutorsSubject>?): String? {
        if (tutorsSubjects == null) {
            return null
        }
        return gson.toJson(tutorsSubjects)
    }

    @TypeConverter
    fun fromJSONToTutorsSubjects(dataString: String?): List<TutorsSubject>? {
        if (dataString == null) {
            return null
        }
        val listType = object : TypeToken<List<TutorsSubject?>?>() {}.type

        return gson.fromJson(dataString, listType)
    }

    @TypeConverter
    fun fromReviewsToJSON(reviews: List<Review>?): String? {
        if (reviews == null) {
            return null
        }
        return gson.toJson(reviews)
    }

    @TypeConverter
    fun fromJSONToReviews(dataString: String?): List<Review>? {
        if (dataString == null) {
            return null
        }
        val listType = object : TypeToken<List<Review?>?>() {}.type

        return gson.fromJson(dataString, listType)
    }
}
