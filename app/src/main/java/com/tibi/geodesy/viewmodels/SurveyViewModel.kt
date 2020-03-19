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
    }

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


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
}