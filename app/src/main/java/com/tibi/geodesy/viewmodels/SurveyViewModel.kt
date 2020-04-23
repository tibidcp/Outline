package com.tibi.geodesy.viewmodels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import app.akexorcist.bluetotohspp.library.BluetoothSPP
import app.akexorcist.bluetotohspp.library.BluetoothState
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
                for (x in 10..100 step 10) {
                    for (y in 10..100 step 10) {
                        val coordinate = Coordinate(x = x.toFloat(), y = y.toFloat(), z = 0.0f)
                        val pointObject = PointObject(type = PointType.METAL_LIGHT.name)
                        database.addPointAndCoordinate(coordinate, pointObject)
                    }
                }

                var coordinate = Coordinate(x = 10f, y = 110f, z = 0.0f)
                var pointObject = PointObject(type = PointType.STATION.name)
                database.addPointAndCoordinate(coordinate, pointObject)
                coordinate = Coordinate(x = 20f, y = 110f, z = 0.0f)
                pointObject = PointObject(type = PointType.STONE_LIGHT.name)
                database.addPointAndCoordinate(coordinate, pointObject)
                coordinate = Coordinate(x = 30f, y = 110f, z = 0.0f)
                pointObject = PointObject(type = PointType.METAL_LIGHT.name)
                database.addPointAndCoordinate(coordinate, pointObject)
                coordinate = Coordinate(x = 40f, y = 110f, z = 0.0f)
                pointObject = PointObject(type = PointType.STONE_POST.name)
                database.addPointAndCoordinate(coordinate, pointObject)
                coordinate = Coordinate(x = 50f, y = 110f, z = 0.0f)
                pointObject = PointObject(type = PointType.METAL_POST.name)
                database.addPointAndCoordinate(coordinate, pointObject)
                coordinate = Coordinate(x = 60f, y = 110f, z = 0.0f)
                pointObject = PointObject(type = PointType.TRAFFIC_LIGHT.name)
                database.addPointAndCoordinate(coordinate, pointObject)
                coordinate = Coordinate(x = 70f, y = 110f, z = 0.0f)
                pointObject = PointObject(type = PointType.WELL.name)
                database.addPointAndCoordinate(coordinate, pointObject)
                coordinate = Coordinate(x = 80f, y = 110f, z = 0.0f)
                pointObject = PointObject(type = PointType.GRID.name)
                database.addPointAndCoordinate(coordinate, pointObject)
                coordinate = Coordinate(x = 90f, y = 110f, z = 0.0f)
                pointObject = PointObject(type = PointType.KILOMETER_SIGN.name)
                database.addPointAndCoordinate(coordinate, pointObject)
                coordinate = Coordinate(x = 100f, y = 110f, z = 0.0f)
                pointObject = PointObject(type = PointType.BUS_STOP_SIGN.name)
                database.addPointAndCoordinate(coordinate, pointObject)
                coordinate = Coordinate(x = 10f, y = 120f, z = 0.0f)
                pointObject = PointObject(type = PointType.POINTER_SIGN.name)
                database.addPointAndCoordinate(coordinate, pointObject)
                coordinate = Coordinate(x = 20f, y = 120f, z = 0.0f)
                pointObject = PointObject(type = PointType.ROAD_SIGN.name)
                database.addPointAndCoordinate(coordinate, pointObject)
                coordinate = Coordinate(x = 30f, y = 120f, z = 0.0f)
                pointObject = PointObject(type = PointType.BUSH.name)
                database.addPointAndCoordinate(coordinate, pointObject)
                coordinate = Coordinate(x = 40f, y = 120f, z = 0.0f)
                pointObject = PointObject(type = PointType.TREE.name)
                database.addPointAndCoordinate(coordinate, pointObject)
                coordinate = Coordinate(x = 50f, y = 120f, z = 0.0f)
                pointObject = PointObject(type = PointType.CONIFER.name)
                database.addPointAndCoordinate(coordinate, pointObject)
                coordinate = Coordinate(x = 60f, y = 120f, z = 0.0f)
                pointObject = PointObject(type = PointType.POINT.name)
                database.addPointAndCoordinate(coordinate, pointObject)


                coordinate = Coordinate(x = 10f, y = 130f, z = 0.0f)
                var coordinate2 = Coordinate(x = 100f, y = 130f, z = 0.0f)
                var coordinate3 = Coordinate(x = 100f, y = 240f, z = 0.0f)
                var linearObject = LinearObject(type = LinearType.SMALL_METAL_FENCE.name)
                var linearObjectId = database.insertLinearObject(linearObject)
                var linearObjectPoint = LinearObjectPoint(linearObjectId = linearObjectId, pointIndex = 0)
                database.addLinearAndCoordinate(coordinate, linearObjectPoint)
                linearObjectPoint = LinearObjectPoint(linearObjectId = linearObjectId, pointIndex = 1)
                database.addLinearAndCoordinate(coordinate2, linearObjectPoint)
                linearObjectPoint = LinearObjectPoint(linearObjectId = linearObjectId, pointIndex = 2)
                database.addLinearAndCoordinate(coordinate3, linearObjectPoint)

                coordinate = Coordinate(x = 10f, y = 140f, z = 0.0f)
                coordinate2 = Coordinate(x = 90f, y = 140f, z = 0.0f)
                coordinate3 = Coordinate(x = 90f, y = 240f, z = 0.0f)
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