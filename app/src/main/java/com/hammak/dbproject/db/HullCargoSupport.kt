package com.hammak.dbproject.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE

@Entity(
    tableName = "hull_cargo_support",
    primaryKeys = ["hullId", "cargoId"],
    foreignKeys = [ForeignKey(
        entity = Hull::class,
        parentColumns = ["id"],
        childColumns = ["hullId"],
        onDelete = CASCADE
    ), ForeignKey(
        entity = Cargo::class,
        parentColumns = ["id"],
        childColumns = ["cargoId"],
        onDelete = CASCADE
    )]
)
data class HullCargoSupport(
    val hullId: Int,
    val cargoId: Int
)
