package com.tibi.geodesy.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.tibi.geodesy.database.*
import kotlinx.coroutines.*

class SurveyViewModel(
    val database: OutlineDao,
    application: Application
) : AndroidViewModel(application)  {
    private var viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
        database.closeDb()
    }

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val pointObjectCoordinates = database.getAllPointObjectCoordinates()
    val linearObjectCoordinates = database.getAllLinearObjectCoordinates()

    fun addQuickStation() {
        uiScope.launch { withContext(Dispatchers.IO) {
            if (database.getStation("S1") != null)
                return@withContext
            val coordinate = Coordinate(x = 0.0f, y = 0.0f, z = 0.0f)
            val station = Station(name = "S1", hi = 0.0f)
            val pointObject = PointObject(type = PointType.STATION.name)
            database.addStation(coordinate, station, pointObject)
        } }
    }

    fun addSomeObjects() {
        uiScope.launch { withContext(Dispatchers.IO) {
            if (pointObjectCoordinates.value?.size == 1) {
                for (x in 100..1000 step 100) {
                    for (y in 100..1000 step 100) {
                        val coordinate = Coordinate(x = x.toFloat(), y = y.toFloat(), z = 0.0f)
                        val pointObject = PointObject(type = PointType.METAL_LIGHT.name)
                        database.addPointAndCoordinate(coordinate, pointObject)
                    }
                }

                var coordinate = Coordinate(x = 100f, y = 1100f, z = 0.0f)
                var pointObject = PointObject(type = PointType.STATION.name)
                database.addPointAndCoordinate(coordinate, pointObject)
                coordinate = Coordinate(x = 200f, y = 1100f, z = 0.0f)
                pointObject = PointObject(type = PointType.STONE_LIGHT.name)
                database.addPointAndCoordinate(coordinate, pointObject)
                coordinate = Coordinate(x = 300f, y = 1100f, z = 0.0f)
                pointObject = PointObject(type = PointType.METAL_LIGHT.name)
                database.addPointAndCoordinate(coordinate, pointObject)
                coordinate = Coordinate(x = 400f, y = 1100f, z = 0.0f)
                pointObject = PointObject(type = PointType.STONE_POST.name)
                database.addPointAndCoordinate(coordinate, pointObject)
                coordinate = Coordinate(x = 500f, y = 1100f, z = 0.0f)
                pointObject = PointObject(type = PointType.METAL_POST.name)
                database.addPointAndCoordinate(coordinate, pointObject)
                coordinate = Coordinate(x = 600f, y = 1100f, z = 0.0f)
                pointObject = PointObject(type = PointType.TRAFFIC_LIGHT.name)
                database.addPointAndCoordinate(coordinate, pointObject)
                coordinate = Coordinate(x = 700f, y = 1100f, z = 0.0f)
                pointObject = PointObject(type = PointType.WELL.name)
                database.addPointAndCoordinate(coordinate, pointObject)
                coordinate = Coordinate(x = 800f, y = 1100f, z = 0.0f)
                pointObject = PointObject(type = PointType.GRID.name)
                database.addPointAndCoordinate(coordinate, pointObject)
                coordinate = Coordinate(x = 900f, y = 1100f, z = 0.0f)
                pointObject = PointObject(type = PointType.KILOMETER_SIGN.name)
                database.addPointAndCoordinate(coordinate, pointObject)
                coordinate = Coordinate(x = 1000f, y = 1100f, z = 0.0f)
                pointObject = PointObject(type = PointType.BUS_STOP_SIGN.name)
                database.addPointAndCoordinate(coordinate, pointObject)
                coordinate = Coordinate(x = 100f, y = 1200f, z = 0.0f)
                pointObject = PointObject(type = PointType.POINTER_SIGN.name)
                database.addPointAndCoordinate(coordinate, pointObject)
                coordinate = Coordinate(x = 200f, y = 1200f, z = 0.0f)
                pointObject = PointObject(type = PointType.ROAD_SIGN.name)
                database.addPointAndCoordinate(coordinate, pointObject)
                coordinate = Coordinate(x = 300f, y = 1200f, z = 0.0f)
                pointObject = PointObject(type = PointType.BUSH.name)
                database.addPointAndCoordinate(coordinate, pointObject)
                coordinate = Coordinate(x = 400f, y = 1200f, z = 0.0f)
                pointObject = PointObject(type = PointType.TREE.name)
                database.addPointAndCoordinate(coordinate, pointObject)
                coordinate = Coordinate(x = 500f, y = 1200f, z = 0.0f)
                pointObject = PointObject(type = PointType.CONIFER.name)
                database.addPointAndCoordinate(coordinate, pointObject)
                coordinate = Coordinate(x = 600f, y = 1200f, z = 0.0f)
                pointObject = PointObject(type = PointType.POINT.name)
                database.addPointAndCoordinate(coordinate, pointObject)


                coordinate = Coordinate(x = 100f, y = 1300f, z = 0.0f)
                var coordinate2 = Coordinate(x = 1000f, y = 1300f, z = 0.0f)
                var coordinate3 = Coordinate(x = 1000f, y = 2400f, z = 0.0f)
                var linearObject = LinearObject(type = LinearType.SMALL_METAL_FENCE.name)
                var linearObjectId = database.insertLinearObject(linearObject)
                var linearObjectPoint = LinearObjectPoint(linearObjectId = linearObjectId, pointIndex = 0)
                database.addLinearAndCoordinate(coordinate, linearObjectPoint)
                linearObjectPoint = LinearObjectPoint(linearObjectId = linearObjectId, pointIndex = 1)
                database.addLinearAndCoordinate(coordinate2, linearObjectPoint)
                linearObjectPoint = LinearObjectPoint(linearObjectId = linearObjectId, pointIndex = 2)
                database.addLinearAndCoordinate(coordinate3, linearObjectPoint)

                coordinate = Coordinate(x = 100f, y = 1400f, z = 0.0f)
                coordinate2 = Coordinate(x = 900f, y = 1400f, z = 0.0f)
                coordinate3 = Coordinate(x = 900f, y = 2400f, z = 0.0f)
                linearObject = LinearObject(type = LinearType.BIG_METAL_FENCE.name)
                linearObjectId = database.insertLinearObject(linearObject)
                linearObjectPoint = LinearObjectPoint(linearObjectId = linearObjectId, pointIndex = 0)
                database.addLinearAndCoordinate(coordinate, linearObjectPoint)
                linearObjectPoint = LinearObjectPoint(linearObjectId = linearObjectId, pointIndex = 1)
                database.addLinearAndCoordinate(coordinate2, linearObjectPoint)
                linearObjectPoint = LinearObjectPoint(linearObjectId = linearObjectId, pointIndex = 2)
                database.addLinearAndCoordinate(coordinate3, linearObjectPoint)
            }
        }}
    }
}