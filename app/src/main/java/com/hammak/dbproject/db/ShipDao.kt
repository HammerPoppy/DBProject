package com.hammak.dbproject.db

import androidx.room.*

@Dao
interface ShipDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertShips(vararg ships: Ship): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertShipsList(ships: List<Ship>): List<Long>

    @Delete
    fun deleteShips(vararg ships: Class)

    @Query("DELETE FROM ship")
    fun deleteAll()

    @Query("SELECT * FROM ship")
    fun getAllShips(): List<Ship>

    @Query("SELECT * FROM ship WHERE price BETWEEN :prices AND :pricee")
    fun getShipsInPriceRange(prices: Int, pricee: Int): List<Ship>
}