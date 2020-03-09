package com.tibi.geodesy.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface OutlineDao {
    @Query("select * from project")
    fun getProjects(): LiveData<List<Project>>

    @Insert
    fun insertProject(project: Project): Long
    @Insert
    fun insertMeasurement(measurement: Measurement): Long
    @Insert
    fun insertStation(station: Station): Long
    @Insert
    fun insertCoordinate(coordinate: Coordinate): Long
    @Insert
    fun insertPointObject(pointObject: PointObject): Long
    @Insert
    fun insertLinearObject(linearObject: LinearObject): Long
    @Insert
    fun insertDrawObject(drawObject: DrawObject): Long
}

@Database(entities = [Project::class, Measurement::class, Station::class, Coordinate::class,
    PointObject::class, LinearObject::class, DrawObject::class], version = 1, exportSchema = false)
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