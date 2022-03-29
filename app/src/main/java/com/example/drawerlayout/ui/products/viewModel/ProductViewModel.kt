package com.example.drawerlayout.ui.products.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.drawerlayout.common.entities.ProductEntity
import com.example.drawerlayout.ui.products.model.ProductInteractor

class ProductViewModel: ViewModel() {

    private val interactor: ProductInteractor = ProductInteractor()

    private val stores = MutableLiveData<MutableList<ProductEntity>>()

//    private val stores: MutableLiveData<MutableList<ProductEntity>> by lazy {
//        MutableLiveData<MutableList<ProductEntity>>().also {
//            loadStores()
//        }
//    }

    fun getStores(): LiveData<MutableList<ProductEntity>> {
        return stores
    }

    fun setProducts() {
        interactor.getStoresApi {
            stores.value = it
        }
    }

//    private fun loadStores() {
//        interactor.getStoresApi {
//            stores.value = it
//        }
//    }
}