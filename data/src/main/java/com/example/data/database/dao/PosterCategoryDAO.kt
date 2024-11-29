package com.example.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.database.contracts.PosterCategoryContrscts
import com.example.data.database.models.PosterCategoryDB

@Dao
interface PosterCategoryDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addAllPosterCategory(newInetList: List<PosterCategoryDB>)


@Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addPosterCategory(newPosterCategory: PosterCategoryDB)

    @Query("DELETE FROM ${PosterCategoryContrscts.TABLE_NAME}")
    fun deletePosterCategory()

    @Query("SELECT * FROM ${PosterCategoryContrscts.TABLE_NAME} WHERE ${PosterCategoryContrscts.Colums.POSTER_ID} =:posterCategoryID")
    fun getPosterCategory(posterCategoryID: Long): List<PosterCategoryDB>
}
