package com.example.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.database.contracts.PosterContrscts
import com.example.data.database.models.PosterDB

@Dao
interface PosterDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addAllPoster(newInetList: List<PosterDB>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addPoster(newPoster: PosterDB)

    @Query("DELETE FROM ${PosterContrscts.TABLE_NAME}")
    fun deletePoster()

    @Query("SELECT * FROM ${PosterContrscts.TABLE_NAME} WHERE ${PosterContrscts.Colums.ID} =:posterID")
    fun getPoster(posterID: Long): List<PosterDB>
}
