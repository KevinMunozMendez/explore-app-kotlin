package com.example.drawerlayout.common.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.drawerlayout.common.entities.CartEntity
import com.example.drawerlayout.common.entities.ProductEntity

@Dao
interface ProductDao {
    @Query("SELECT * FROM cartEntity")
    fun getAllProducts(): LiveData<MutableList<CartEntity>>

    @Query("SELECT * FROM cartEntity where id = :id")
    fun getProductById(id: Long): LiveData<CartEntity>

    @Insert
    suspend fun addProduct(cartEntity: CartEntity): Long

    @Update
    suspend fun updateProduct(cartEntity: CartEntity): Int

    @Delete
    suspend fun deleteProduct(cartEntity: CartEntity): Int
}