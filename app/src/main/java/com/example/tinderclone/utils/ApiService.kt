package com.example.tinderclone.utils

import com.example.tinderclone.models.Activity
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

        @GET("activity")
        fun getUser(): Call<Activity>

}