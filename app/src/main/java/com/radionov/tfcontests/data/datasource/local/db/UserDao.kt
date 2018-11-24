package com.radionov.tfcontests.data.datasource.local.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.radionov.tfcontests.data.entities.User
import io.reactivex.Single

/**
 * @author Andrey Radionov
 */
@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getUser() : Single<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveProfile(user: User)

    @Query("DELETE FROM user")
    fun removeProfile()
}
