package com.example.drawerlayout.common.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "cartEntity", indices = [Index(value = ["name"], unique = true)])
data class CartEntity(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var coverImage: String,
    var price: Int,
    var description: String,
    var name: String,
    var amount: Int = 1
)
