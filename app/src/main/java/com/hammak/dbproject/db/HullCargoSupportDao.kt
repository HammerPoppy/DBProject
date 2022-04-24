package com.hammak.dbproject.db

import androidx.room.*

@Dao
interface HullCargoSupportDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHullCargoSupport(vararg hullCargoSupport: HullCargoSupport): List<Long>

    @Delete
    fun deleteHullCargoSupport(vararg hullCargoSupport: HullCargoSupport)

    @Query("DELETE FROM hull_cargo_support")
    fun deleteAll()

    @Query("SELECT * FROM hull_cargo_support")
    fun getAllHullCargoSupport(): Array<HullCargoSupport>

    @Query("SELECT * FROM cargo INNER JOIN hull_cargo_support ON cargo.id = hull_cargo_support.cargoId WHERE hull_cargo_support.hullId=:hullId")
    fun getCargoForHull(hullId: Int): List<Cargo>

    @Transaction
    @Query("SELECT * FROM cargo")
    fun getCargo(): List<HullCargoPair>
}