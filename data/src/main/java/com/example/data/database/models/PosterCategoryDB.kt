package com.example.data.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.database.contracts.PosterCategoryContrscts

@Entity(
    tableName = PosterCategoryContrscts.TABLE_NAME,
//    foreignKeys = [
//        ForeignKey(
//            entity = PosterDB::class,
//            parentColumns = [PosterContrscts.Colums.ID],
//            childColumns = [PosterCategoryContrscts.Colums.POSTER_ID],
//        ), ForeignKey(
//            entity = CategoriesDB::class,
//            parentColumns = [CategorieContrscts.Colums.ID],
//            childColumns = [PosterCategoryContrscts.Colums.CATEGORY_ID],
//        ),
//    ],
)
data class PosterCategoryDB(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = PosterCategoryContrscts.Colums.ID)
    val id: Long? = null,
    @ColumnInfo(name = PosterCategoryContrscts.Colums.POSTER_ID)
    val posterId: Long,
    @ColumnInfo(name = PosterCategoryContrscts.Colums.CATEGORY_ID)
    val categoryId: Long,
)
