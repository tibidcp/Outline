package com.tibi.geodesy.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
data class Projects constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val instrument: String
)

@Entity
data class Measurements constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val va: Double,
    val ha: Double,
    val sd: Double,
    val ht: Double,
    @ForeignKey(entity = Projects::class, parentColumns = ["id"], childColumns = ["projectId"])
    val projectId: Long,
    @ForeignKey(entity = Stations::class, parentColumns = ["id"], childColumns = ["StationId"])
    val StationId: Long,
    @ForeignKey(entity = Stations::class, parentColumns = ["id"], childColumns = ["backsightId"])
    val backsightId: Long
)

@Entity
data class Stations constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val hi: Double,
    val x: Double,
    val y: Double,
    val z: Double
)

@Entity
data class Coordinates constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ForeignKey(entity = Measurements::class, parentColumns = ["id"], childColumns = ["measurementId"])
    val measurementId: Long,
    val x: Double,
    val y: Double,
    val z: Double
)

@Entity
data class PointObjects constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ForeignKey(entity = Coordinates::class, parentColumns = ["id"], childColumns = ["coordinateId"])
    val coordinateId: Long,
    val angle: Double,
    val textAttribute: String,
    @ForeignKey(entity = AllObjects::class, parentColumns = ["id"], childColumns = ["objectId"])
    val objectId: Long
)

@Entity
data class LinearObjects constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ForeignKey(entity = Coordinates::class, parentColumns = ["id"], childColumns = ["coordinateId"])
    val coordinateId: Long,
    @ForeignKey(entity = AllObjects::class, parentColumns = ["id"], childColumns = ["objectId"])
    val objectId: Long,
    val pointIndex: Int
)

@Entity
data class AllObjects constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val type: String,
    val color: Int,
    val layer: String,
    val lineType: String,
    val lineWeight: Double
)



