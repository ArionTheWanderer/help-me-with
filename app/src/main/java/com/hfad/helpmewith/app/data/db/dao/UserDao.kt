package com.hfad.helpmewith.app.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.hfad.helpmewith.app.data.db.model.User

@Dao
interface UserDao {
    @Query("DELETE FROM users WHERE id = :id")
    suspend fun deleteUser(id: Long): Int

    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getUser(id: Long): User?
}
