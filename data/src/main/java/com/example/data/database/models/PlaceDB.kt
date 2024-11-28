package com.example.data.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.database.contracts.PlaceContrscts

@Entity(
    tableName = PlaceContrscts.TABLE_NAME,
)
data class PlaceDB(
    @ColumnInfo(name = PlaceContrscts.Colums.ADDRESS)
    val address: String?,
    @ColumnInfo(name = PlaceContrscts.Colums.BODY_TEXT)
    val bodyText: String?,
    @ColumnInfo(name = PlaceContrscts.Colums.LAT)
    val lat: Double,
    @ColumnInfo(name = PlaceContrscts.Colums.LON)
    val lon: Double,
    @ColumnInfo(name = PlaceContrscts.Colums.DESC)
    val description: String?,
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = PlaceContrscts.Colums.ID)
    val id: Long,
    @ColumnInfo(name = PlaceContrscts.Colums.IMAGE)
    val image: String?,
    @ColumnInfo(name = PlaceContrscts.Colums.PHONE)
    val phone: String,
    @ColumnInfo(name = PlaceContrscts.Colums.SHORT_TITLE)
    val shortTitle: String?,
    @ColumnInfo(name = PlaceContrscts.Colums.SITE_URL)
    val siteUrl: String?,
    @ColumnInfo(name = PlaceContrscts.Colums.TIME_TABLE)
    val timetable: String?,
    @ColumnInfo(name = PlaceContrscts.Colums.TITLE)
    val title: String,
    @ColumnInfo(name = PlaceContrscts.Colums.CITY)
    val city: String
)
