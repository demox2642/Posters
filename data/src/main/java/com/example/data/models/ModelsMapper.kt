package com.example.data.models

import com.example.data.database.models.PlaceDB
import com.example.data.database.models.PosterDB
import com.example.domain.models.Location
import com.example.domain.models.PlacePresentation
import com.example.domain.models.PosterPresentation
import java.util.Date

fun Place.toPlaceDB()=PlaceDB(
    id = this.id,
    address = this.address,
    bodyText = this.bodyText,
    description = this.description,
    lon = this.coords.lon,
    lat = this.coords.lat,
    image = this.images?.firstOrNull()?.image,
    phone = this.phone,
    siteUrl = this.siteUrl,
    shortTitle = this.shortTitle,
    timetable = this.timetable,
    title = this.title,
    city = this.location
)

fun PlaceDB.toPlacePresentation() = PlacePresentation(
    address = this.address,
    bodyText = this.bodyText,
    description = this.description,
    phone = this.phone,
    city = this.city,
    title = this.title,
    timetable = this.timetable,
    siteUrl = this.siteUrl,
    shortTitle = this.shortTitle,
    images = this.image,
    id = this.id,
    location = Location(lat = this.lat, lon = this.lon)
)

 fun Event.toPosterDB(): PosterDB{

    return PosterDB(
    id = this.id,
    startData = this.dates.lastOrNull()?.start,
    endData = this.dates.lastOrNull()?.end,
    description = this.description,
    title = this.title,
    images = this.images.firstOrNull()?.image,
    placeId = this.place!!.id,
    slug = this.slug
)}

fun PosterDB.toPosterPresentation(categories: List<String>, placeDB: PlaceDB) = PosterPresentation(
     id = this.id,
 location = Location(lon = placeDB.lon, lat = placeDB.lat),
 categories = categories,
 startData = if (this.startData != null) Date(this.startData) else null,
 endData= if (this.endData != null) Date(this.endData) else null,
 title = this.title,
 description = this.description,
)

fun Event.toPosterPresentation(categories: List<String>)= PosterPresentation(
    id = this.id,
    location = Location(lon = this.coords?.lon, lat = this.coords?.lat),
    categories = categories,
    startData = this.dates.firstOrNull()?.let { Date(it.start) },
    endData= this.dates.firstOrNull()?.let { Date(it.end) },
    title = this.title,
    description = this.description,
)


