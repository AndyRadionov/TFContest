package com.radionov.tfcontests.data.datasource.local.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.radionov.tfcontests.data.entities.User

/**
 * @author Andrey Radionov
 */
@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val userDao: UserDao
}