package com.tibi.geodesy.database

import android.graphics.Color
import androidx.room.*

enum class PointType {
    STATION, BACKSIGHT
}

@Entity(indices = [Index("stationName"), Index("backsightName")],
    foreignKeys =
[ForeignKey(entity = Station::class, parentColumns = ["name"], childColumns = ["stationName"]),
    ForeignKey(entity = Station::class, parentColumns = ["name"], childColumns = ["backsightName"])])
data class Measurement constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    var va: Double,
    var ha: Double,
    var sd: Double,
    var ht: Double,
    var stationName: String,
    var backsightName: String
)

@Entity(indices = [Index("coordinateId")],
    foreignKeys =
[ForeignKey(entity = Coordinate::class, parentColumns = ["id"], childColumns = ["coordinateId"])])
data class Station constructor(
    @PrimaryKey
    var name: String,
    var hi: Double,
    var coordinateId: Long = 0
)

@Entity
data class Coordinate constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    var x: Double,
    var y: Double,
    var z: Double
)

@Entity(indices = [Index("coordinateId")],
    foreignKeys =
[ForeignKey(entity = Coordinate::class, parentColumns = ["id"], childColumns = ["coordinateId"])])
data class PointObject constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    var coordinateId: Long = 0,
    var angle: Double = 0.0,
    var textAttribute: String = "",
    var type: String,
    var color: Int = Color.BLACK,
    var layer: String = "",
    var weight: Double = 1.0
)

@Entity(indices = [Index("coordinateId")],
    foreignKeys =
[ForeignKey(entity = Coordinate::class, parentColumns = ["id"], childColumns = ["coordinateId"])])
data class LinearObject constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    var coordinateId: Long = 0,
    var pointIndex: Int,
    var type: String,
    var color: Int = Color.BLACK,
    var layer: String = "",
    var weight: Double = 1.0
)

data class PointObjectCoordinate constructor(
    val angle: Double,
    val textAttribute: String,
    val type: String,
    val color: Int,
    val layer: String,
    val weight: Double,
    val x: Double,
    val y: Double,
    val z: Double
)



