package com.example.drawerlayout.ui.cart.adapter

import android.view.View
import androidx.annotation.MenuRes
import com.example.drawerlayout.common.entities.CartEntity

interface CartListener {
    fun onClick (cartEntity: CartEntity, v: View, @MenuRes menuRes: Int)
    fun swipe(cartEntity: CartEntity)
}