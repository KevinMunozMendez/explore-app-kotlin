package com.example.drawerlayout.ui.cart.model

import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.drawerlayout.ProductApplication
import com.example.drawerlayout.common.entities.CartEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class CartInteractor {

    val stores: LiveData<MutableList<CartEntity>> = liveData {
        val itemsLiveData = ProductApplication.database.ProductDao().getAllProducts()
        emitSource(itemsLiveData)
    }

    suspend fun addItem(cartEntity: CartEntity) = withContext(Dispatchers.IO) {
        ProductApplication.database.ProductDao().addProduct(cartEntity)
    }

    suspend fun deleteItem(cartEntity: CartEntity) = withContext(Dispatchers.IO) {
        ProductApplication.database.ProductDao().deleteProduct(cartEntity)
    }
}