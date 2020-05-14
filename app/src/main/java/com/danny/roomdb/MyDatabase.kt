package com.danny.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
abstract class MyDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile private var database: MyDatabase? = null

//        @Synchronized
        @JvmStatic
        fun instance(context: Context): MyDatabase {
            return database ?: synchronized(this) {
                database ?: build(context).also { database = it }
            }
        }

        private fun build(context: Context) = Room.databaseBuilder(context.applicationContext, MyDatabase::class.java, "MyDatabase").build()
    }
}