package com.example.drawerlayout.ui.cart.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drawerlayout.common.entities.CartEntity
import com.example.drawerlayout.ui.cart.model.CartInteractor
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class CartViewModel: ViewModel() {
    private val interactor: CartInteractor = CartInteractor()

    private val stores = interactor.stores

    fun getItemsCart(): LiveData<MutableList<CartEntity>> {
        return stores
    }

    fun addItem(cartEntity: CartEntity) {
        executeAction { interactor.addItem(cartEntity) }
    }

    fun deleteItem(cartEntity: CartEntity) {
        executeAction { interactor.deleteItem(cartEntity) }
    }

    private fun executeAction( block: suspend() -> Unit): Job {
        return viewModelScope.launch {
            try {
                block()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}