package com.oseong.ifeelalive.data.source.api

import com.oseong.ifeelalive.data.AstroPictureResponse
import com.oseong.ifeelalive.utils.Utils
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaService {
    @GET("planetary/apod")
    suspend fun getAstroPictures(
        @Query("start_date") startData: String,
        @Query("end_date") endData: String,
        @Query("thumbs") thumbs: Boolean = true
    )

    @GET("planetary/apod")
    suspend fun getAstroPictureFromData(
        @Query("date") date: String = Utils.getTodayDate()
    )

    @GET("planetary/apod")
    fun getRandomAstroPictures(
        @Query("count") count: Int = 1,
        @Query("thumbs") thumbs: Boolean = true
    ): Call<List<AstroPictureResponse>>
}