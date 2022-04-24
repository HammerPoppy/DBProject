package com.hammak.dbproject.db

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class HullCargoPair(
    @Embedded
    var hull: Hull,
    @Relation(
        parentColumn = "id",
        entity = Cargo::class,
        entityColumn = "id",
        associateBy = Junction(
            value = HullCargoSupport::class,
            parentColumn = "hullId",
            entityColumn = "cargoId"
        )
    )
    var cargo: List<Cargo>
)
