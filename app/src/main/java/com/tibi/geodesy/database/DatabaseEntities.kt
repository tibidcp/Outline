package com.tibi.geodesy.database

import android.graphics.Color
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity()
data class Project constructor(
    @PrimaryKey
    val name: String,
    val instrument: String = "Nikon"
)

@Entity(foreignKeys =
[ForeignKey(entity = Project::class, parentColumns = ["name"], childColumns = ["projectName"], onDelete = ForeignKey.CASCADE),
    ForeignKey(entity = Station::class, parentColumns = ["id"], childColumns = ["stationId"]),
    ForeignKey(entity = Station::class, parentColumns = ["id"], childColumns = ["backsightId"])])
data class Measurement constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val va: Double,
    val ha: Double,
    val sd: Double,
    val ht: Double,
    val projectName: String,
    val stationId: Long,
    val backsightId: Long
)

@Entity(foreignKeys =
[ForeignKey(entity = Project::class, parentColumns = ["name"], childColumns = ["projectName"], onDelete = ForeignKey.CASCADE),
    ForeignKey(entity = Coordinate::class, parentColumns = ["id"], childColumns = ["coordinateId"])])
data class Station constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String,
    val hi: Double,
    val coordinateId: Long,
    val projectName: String
)

@Entity(foreignKeys =
[ForeignKey(entity = Project::class, parentColumns = ["name"], childColumns = ["projectName"], onDelete = ForeignKey.CASCADE)])
data class Coordinate constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val x: Double,
    val y: Double,
    val z: Double,
    val projectName: String
)

@Entity(foreignKeys =
[ForeignKey(entity = Project::class, parentColumns = ["name"], childColumns = ["projectName"], onDelete = ForeignKey.CASCADE),
    ForeignKey(entity = Coordinate::class, parentColumns = ["id"], childColumns = ["coordinateId"])])
data class PointObject constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val coordinateId: Long,
    val angle: Double = 0.0,
    val textAttribute: String = "",
    val projectName: String,
    val type: String,
    val color: Int = Color.BLACK,
    val layer: String = "",
    val weight: Double = 1.0
)

@Entity(foreignKeys =
[ForeignKey(entity = Project::class, parentColumns = ["name"], childColumns = ["projectName"], onDelete = ForeignKey.CASCADE),
    ForeignKey(entity = Coordinate::class, parentColumns = ["id"], childColumns = ["coordinateId"])])
data class LinearObject constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val coordinateId: Long,
    val pointIndex: Int,
    val projectName: String,
    val type: String,
    val color: Int = Color.BLACK,
    val layer: String = "",
    val weight: Double = 1.0
)



