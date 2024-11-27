package com.example.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.database.contracts.CategorieContrscts
import com.example.data.database.models.CategoriesDB

@Dao
interface CategoriesDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addAllCategorie(newInetList: List<CategoriesDB>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addCategorie(newInet: CategoriesDB)

    @Query("DELETE FROM ${CategorieContrscts.TABLE_NAME}")
    fun deleteCategorie()

    @Query("SELECT * FROM ${CategorieContrscts.TABLE_NAME} WHERE ${CategorieContrscts.Colums.ID} =:posterID")
    fun getCategorie(posterID: Long): List<CategoriesDB>
}
