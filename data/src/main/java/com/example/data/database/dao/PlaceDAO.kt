package com.example.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.database.contracts.PlaceContrscts
import com.example.data.database.models.PlaceDB

@Dao
interface PlaceDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addAllPlace(newInetList: List<PlaceDB>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addPlace(newPlace: PlaceDB)

    @Query("DELETE FROM ${PlaceContrscts.TABLE_NAME}")
    fun deletePlace()

    @Query("SELECT * FROM ${PlaceContrscts.TABLE_NAME} WHERE ${PlaceContrscts.Colums.ID} =:placeID")
    fun getPlace(placeID: Long): PlaceDB
}
