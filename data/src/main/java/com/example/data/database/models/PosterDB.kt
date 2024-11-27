package com.example.data.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.data.database.contracts.PlaceContrscts
import com.example.data.database.contracts.PosterContrscts

@Entity(
    tableName = PosterContrscts.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = PlaceDB::class,
            parentColumns = [PlaceContrscts.Colums.ID],
            childColumns = [PosterContrscts.Colums.PLACE_ID],
        ),
    ],
)
data class PosterDB(
    @ColumnInfo(name = PosterContrscts.Colums.START_DATA)
    val startData: Long,
    @ColumnInfo(name = PosterContrscts.Colums.END_DATA)
    val endData: Long,
    @ColumnInfo(name = PosterContrscts.Colums.DESCRIPTION)
    val description: String,
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = PosterContrscts.Colums.ID)
    val id: Long,
    @ColumnInfo(name = PosterContrscts.Colums.IMAGE)
    val images: String,
    @ColumnInfo(name = PosterContrscts.Colums.PLACE_ID)
    val placeId: Long,
    @ColumnInfo(name = PosterContrscts.Colums.SLUG)
    val slug: String,
    @ColumnInfo(name = PosterContrscts.Colums.TITLE)
    val title: String,
)
