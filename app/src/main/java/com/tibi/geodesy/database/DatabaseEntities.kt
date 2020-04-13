package com.tibi.geodesy.database

import android.graphics.Color
import androidx.room.*

enum class LinearType {
    FENCE
}

@Entity(indices = [Index("stationName"), Index("backsightName")],
    foreignKeys =
[ForeignKey(entity = Station::class, parentColumns = ["name"], childColumns = ["stationName"]),
    ForeignKey(entity = Station::class, parentColumns = ["name"], childColumns = ["backsightName"])])
data class Measurement constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    var va: Float,
    var ha: Float,
    var sd: Float,
    var ht: Float,
    var stationName: String,
    var backsightName: String
)

@Entity(indices = [Index("coordinateId")],
    foreignKeys =
[ForeignKey(entity = Coordinate::class, parentColumns = ["id"], childColumns = ["coordinateId"])])
data class Station constructor(
    @PrimaryKey
    var name: String,
    var hi: Float,
    var coordinateId: Long = 0
)

@Entity
data class Coordinate constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    var x: Float,
    var y: Float,
    var z: Float
)

@Entity(indices = [Index("coordinateId")],
    foreignKeys =
[ForeignKey(entity = Coordinate::class, parentColumns = ["id"], childColumns = ["coordinateId"])])
data class PointObject constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    var coordinateId: Long = 0,
    var angle: Float = 0.0f,
    var textAttribute: String = "",
    var type: String,
    var color: Int = Color.BLACK,
    var layer: String = "",
    var weight: Float = 1.0f
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
    var weight: Float = 1.0f
)

data class PointObjectCoordinate constructor(
    val angle: Float,
    val textAttribute: String,
    val type: String,
    val color: Int,
    val layer: String,
    val weight: Float,
    val x: Float,
    val y: Float,
    val z: Float
)

data class LinearObjectCoordinate constructor(
    var pointIndex: Int,
    var type: String,
    var color: Int,
    var layer: String,
    var weight: Float,
    val x: Float,
    val y: Float,
    val z: Float
)



enum class PointType {
    STATION,
    BACK_SIGHT,
    STONE_LIGHT,
    METAL_LIGHT,
    STONE_POST,
    METAL_POST,
    TRAFFIC_LIGHT,
    WELL,
    GRID,
    KILOMETER_SIGN,
    BUS_STOP_SIGN,
    POINTER_SIGN,
    ROAD_SIGN,
    BUSH,
    TREE,
    CONIFER,
    POINT
}



