package com.example.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.database.contracts.CategorieContrscts
import com.example.data.database.models.CategoriesDB

@Dao
interface CategoriesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAllCategorie(categoryList: List<CategoriesDB>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCategorie(category: CategoriesDB)

    @Query("DELETE FROM ${CategorieContrscts.TABLE_NAME}")
    fun deleteCategorie()

    @Query("SELECT * FROM ${CategorieContrscts.TABLE_NAME} WHERE ${CategorieContrscts.Colums.ID} =:category")
    fun getCategorie(category: Long): CategoriesDB

    @Query("SELECT * FROM ${CategorieContrscts.TABLE_NAME} WHERE ${CategorieContrscts.Colums.SLUG} =:name")
    fun getCategorieByName(name: String): CategoriesDB

    @Query("SELECT * FROM ${CategorieContrscts.TABLE_NAME}")
    fun getCategoryList():List<CategoriesDB>
}
