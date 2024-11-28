package com.example.data.service

import com.example.data.models.Category
import com.example.data.models.Place
import com.example.data.models.ServerResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface KudaGoService {
    @GET("event-categories/")
    suspend fun getCategoryList(): List<Category>

    @GET("events/")
    suspend fun getEvents(
        @Query("page") page: Int,
        @Query("fields") fields: String = "id,place,description,slug,title,description,dates,images,categories",
        @Query("text_format") textFormat: String = "text",
        @Query("actual_since") startDate: Long,
        @Query("categories") categories: String?,
        @Query("lon") lon: Long?,
        @Query("lat") lat: Long?,
        @Query("radius") radius: Long?,
    ): ServerResponse

    @GET("places/{id}/?text_format=text")
    suspend fun getPlace(
        @Path("id") id: String,
    ): Place
}
