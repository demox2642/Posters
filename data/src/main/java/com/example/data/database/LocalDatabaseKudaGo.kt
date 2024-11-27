package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.database.dao.CategoriesDAO
import com.example.data.database.dao.PlaceDAO
import com.example.data.database.dao.PosterCategoryDAO
import com.example.data.database.dao.PosterDAO
import com.example.data.database.models.CategoriesDB
import com.example.data.database.models.PlaceDB
import com.example.data.database.models.PosterCategoryDB
import com.example.data.database.models.PosterDB

@Database(
    entities = [
        CategoriesDB::class,
        PlaceDB::class,
        PosterDB::class,
        PosterCategoryDB::class,

    ],
    version = 1,
    exportSchema = false,
)
abstract class LocalDatabaseKudaGo : RoomDatabase() {
    abstract fun categoryDao(): CategoriesDAO

    abstract fun placeDao(): PlaceDAO

    abstract fun posterCategoryDao(): PosterCategoryDAO

    abstract fun posterDao(): PosterDAO
}
