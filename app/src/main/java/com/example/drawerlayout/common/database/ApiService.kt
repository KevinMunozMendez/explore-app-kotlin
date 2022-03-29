package com.example.drawerlayout.common.database

import com.example.drawerlayout.common.entities.ProductEntity
import retrofit2.http.GET

interface ApiService {
    @GET("/api/cities")
    suspend fun getAllCities(): MutableList<ProductEntity>
}