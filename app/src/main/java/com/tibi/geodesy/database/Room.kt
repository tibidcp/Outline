package com.tibi.geodesy.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface OutlineDao {
    @Query("select * from projects")
    fun getProjects(): LiveData<List<Projects>>
}

@Database(entities = [Projects::class, Measurements::class, Stations::class, Coordinates::class,
    PointObjects::class, LinearObjects::class, AllObjects::class], version = 1)
abstract class OutlineDatabase : RoomDatabase() {
    abstract val outlineDao: OutlineDao
}

private lateinit var INSTANCE: OutlineDatabase

fun getDatabase(context: Context): OutlineDatabase {
    synchronized(OutlineDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                OutlineDatabase::class.java,
                "outline").build()
        }
    }
    return INSTANCE
}