package com.example.drawerlayout.ui.products.model

import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.drawerlayout.ProductApplication
import com.example.drawerlayout.common.entities.ProductEntity
import com.example.drawerlayout.common.utils.Constants
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ProductInteractor {

    fun getStoresApi(callback: (MutableList<ProductEntity>) -> Unit) {
        val url = Constants.URL + Constants.GET_ALL_STORES
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, { response ->
            val status = response.optBoolean(Constants.STATUS_PROPERTY, Constants.ERROR)
            if (status == Constants.SUCCESS) {
                val jsonList: String? = response.optJSONArray(Constants.PRODUCTS_PROPERTY)?.toString()
                if (jsonList != null) {
                    val mutableListType = object : TypeToken<MutableList<ProductEntity>>(){}.type
                    val productsLiveData = Gson().fromJson<MutableList<ProductEntity>>(jsonList, mutableListType)
                    callback(productsLiveData)
                }
            }
        },{
            it.printStackTrace()
        })

        ProductApplication.productAPI.addToRequestQueue(jsonObjectRequest)
    }

//    suspend fun getStoresApi() = withContext(Dispatchers.IO) {
//        val response = CitiesApplication.service.getAllCities()
//        Log.i("RESPUESTA DEL SERVIDOR",response.toString())
//    }


//    val stores: LiveData<MutableList<CitiesEntity>> = liveData {
//        val response = CitiesApplication.service.getAllCities()
//        var arr: MutableList<CitiesEntity> = response
//        CitiesApplication.database.CityDao().insertCities(arr)
//        val citiesLiveData = CitiesApplication.database.CityDao().getAllCities()
//        Log.i("chayaneeee",citiesLiveData.toString())
//        emitSource(citiesLiveData)
//    }
}
