package com.hammak.dbproject.db

import androidx.room.*

@Dao
interface CargoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCargo(vararg cargo: Cargo): List<Long>

    @Delete
    fun deleteCargo(vararg cargo: Cargo)

    @Query("DELETE FROM cargo")
    fun deleteAll()

    @Query("SELECT * FROM cargo")
    fun getAllCargo(): Array<Cargo>
}