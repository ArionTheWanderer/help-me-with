package com.hfad.helpmewith.app.data.db.model

import androidx.room.*
import com.hfad.helpmewith.app.data.db.converter.Converters

@Entity(tableName = "users")
data class User (
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "first_name") val firstName:String,
    @ColumnInfo(name = "last_name") val lastName:String,
    val login: String,
    @ColumnInfo(name = "is_tutor") val isTutor: Boolean,
    val rating: Double,
    @Embedded
    val tutorInfo: TutorInfo?,
    @TypeConverters(Converters::class)
    val tutorsSubjects: List<TutorsSubject>?,
    @TypeConverters(Converters::class)
    val reviews: List<Review>?
)

data class TutorInfo (
    @ColumnInfo(name = "is_not_remote") val isNotRemote: Boolean?,
    val city: String?,
)
