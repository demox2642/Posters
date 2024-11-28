package com.example.data.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.database.contracts.CategorieContrscts

@Entity(
    tableName = CategorieContrscts.TABLE_NAME,
)
data class CategoriesDB(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = CategorieContrscts.Colums.ID)
    val id: Long,
    @ColumnInfo(name = CategorieContrscts.Colums.NAME)
    val name: String,
    @ColumnInfo(name = CategorieContrscts.Colums.SLUG)
    val slug: String,
    @ColumnInfo(name = CategorieContrscts.Colums.SELECT)
    val select: Boolean = false,
)
