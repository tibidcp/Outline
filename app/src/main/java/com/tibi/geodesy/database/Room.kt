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
    @Insert
    fun insertLinearObjectPoint(linearObjectPoint: LinearObjectPoint): Long

    @Transaction
    fun addStation(coordinate: Coordinate, station: Station, pointObject: PointObject){
        val coordinateId = insertCoordinate(coordinate)
        station.coordinateId = coordinateId
        pointObject.coordinateId = coordinateId
        insertStation(station)
        insertPointObject(pointObject)
    }

    @Transaction
    fun  addPointAndCoordinate(coordinate: Coordinate, pointObject: PointObject){
        val coordinateId = insertCoordinate(coordinate)
        pointObject.coordinateId = coordinateId
        insertPointObject(pointObject)
    }

    @Transaction
    fun  addLinearAndCoordinate(coordinate: Coordinate, linearObjectPoint: LinearObjectPoint){
        val coordinateId = insertCoordinate(coordinate)
        linearObjectPoint.coordinateId = coordinateId
        insertLinearObjectPoint(linearObjectPoint)
    }

    @Transaction
    @Query("select angle, textAttribute, type, color, layer, weight, x, y, z " +
            "from pointobject, coordinate where coordinate.id == pointobject.coordinateId")
    fun getAllPointObjectCoordinates(): LiveData<List<PointObjectCoordinate>>

    @Transaction
    @Query("select linearObjectId, pointIndex, type, color, layer, weight, closed, x, y, z from linearobjectpoint, " +
            "coordinate, linearobject where coordinate.id == linearobjectpoint.coordinateId and linearobject.id == linearobjectpoint.linearObjectId")
    fun getAllLinearObjectCoordinates(): LiveData<List<LinearObjectCoordinate>>

    @Query("select * from station where name = :name")
    fun getStation(name: String): Station?

    fun closeDb() {
        INSTANCE.close()
    }
}

@Database(entities = [Measurement::class, Station::class, Coordinate::class,
    PointObject::class, LinearObject::class, LinearObjectPoint::class], version = 1, exportSchema = false)
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