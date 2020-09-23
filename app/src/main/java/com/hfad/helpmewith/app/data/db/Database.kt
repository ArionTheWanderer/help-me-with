package com.hfad.helpmewith.app.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hfad.helpmewith.app.data.db.converter.Converters
import com.hfad.helpmewith.app.data.db.dao.UserDao
import com.hfad.helpmewith.app.data.db.model.User

@Database(entities = [(User::class)], version = 1)
@TypeConverters(Converters::class)
abstract class Database: RoomDatabase() {
    abstract fun userDao() : UserDao
}
