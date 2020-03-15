package com.tibi.geodesy.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface OutlineDao {
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

    @Transaction
    fun addStation(coordinate: Coordinate, station: Station, pointObject: PointObject){
        val coordinateId = insertCoordinate(coordinate)
        station.coordinateId = coordinateId
        pointObject.coordinateId = coordinateId
        insertStation(station)
        insertPointObject(pointObject)
    }

    @Query("select * from station where name = :name")
    fun getStation(name: String): Station?
}

@Database(entities = [Measurement::class, Station::class, Coordinate::class,
    PointObject::class, LinearObject::class], version = 1, exportSchema = false)
abstract class OutlineDatabase : RoomDatabase() {
    abstract val outlineDao: OutlineDao
}

private lateinit var INSTANCE: OutlineDatabase

fun getDatabase(context: Context, projectName: String): OutlineDatabase {
    synchronized(OutlineDatabase::class.java) {
        if (!::INSTANCE.isInitialized || ::INSTANCE.name != "outline_$projectName") {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                OutlineDatabase::class.java,
                "outline_$projectName").build()
        }
    }
    return INSTANCE
}