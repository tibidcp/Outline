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
            val coordinate = Coordinate(x = 0.0, y = 0.0, z = 0.0)
            val station = Station(name = "S1", hi = 0.0)
            val pointObject = PointObject(type = PointType.STATION.name)
            database.addStation(coordinate, station, pointObject)
        } }
    }

    fun addSomeObjects() {
        uiScope.launch { withContext(Dispatchers.IO) {
            if (pointObjectCoordinates.value?.size == 1) {
                var pointIndex = 1
                for (x in 100..1000 step 100) {
                    for (y in 100..1000 step 100) {
                        val coordinate = Coordinate(x = x.toDouble(), y = y.toDouble(), z = 0.0)
                        val pointObject = PointObject(type = PointType.STOLB.name)
                        val linearObject = LinearObject(pointIndex = pointIndex,
                            type = LinearType.FENCE.name)
                        pointIndex++
                        database.addLineAndCoordinate(coordinate, linearObject)
                        database.addPointAndCoordinate(coordinate, pointObject)
                    }
                }
            }
        }}
    }
}