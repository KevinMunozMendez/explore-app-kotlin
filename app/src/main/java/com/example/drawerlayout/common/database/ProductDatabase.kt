package com.example.drawerlayout.common.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.drawerlayout.common.entities.CartEntity
import com.example.drawerlayout.common.entities.ProductEntity


@Database(entities = [CartEntity::class], version = 2)
abstract class ProductDatabase: RoomDatabase() {
    abstract fun ProductDao(): ProductDao
}