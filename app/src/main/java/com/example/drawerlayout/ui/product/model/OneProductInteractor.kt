package com.example.drawerlayout.ui.product.model

import android.util.Log
import com.android.volley.AuthFailureError
import com.android.volley.toolbox.JsonObjectRequest
import com.example.drawerlayout.ProductApplication
import com.example.drawerlayout.common.entities.LoggedInUser
import com.example.drawerlayout.common.entities.ProductEntity
import com.example.drawerlayout.common.utils.Constants
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

class OneProductInteractor {

    fun addComment(comment: String, token: String, idArticle: String, callback: (ProductEntity) -> Unit) {
        val url = Constants.URL + Constants.POST_COMMENT + idArticle
        val jsonParams = JSONObject()
        if (comment.isNotEmpty()) jsonParams.put(Constants.COMMENT_PARAM, comment)
//        if (token.isNotEmpty()) jsonParams.put("token", token)

        val jsonObjectRequest = object : JsonObjectRequest(Method.POST, url, jsonParams, { response ->
            val status = response.optBoolean(Constants.STATUS_PROPERTY, Constants.ERROR)
            if (status == Constants.SUCCESS) {
                val jsonObject: String? = response.optJSONObject(Constants.PRODUCTS_PROPERTY)?.toString()
                if (jsonObject != null) {
                    val objectType = object : TypeToken<ProductEntity>(){}.type
                    val objectLiveData = Gson().fromJson<ProductEntity>(jsonObject,objectType)
                    callback(objectLiveData)
                }
            }
        },{
            it.printStackTrace()
        }) {

            @Throws(AuthFailureError::class)
            override fun getHeaders(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["Content-Type"] = "application/json"
                params["Authorization"] = "Bearer $token"
                return params
            }
        }

        ProductApplication.productAPI.addToRequestQueue(jsonObjectRequest)
    }

    fun deleteComment(idComment: String, idArticle: String, callback: (ProductEntity) -> Unit) {
        val url = Constants.URL + Constants.POST_COMMENT + idArticle + "/" + idComment
        val jsonObjectRequest = object : JsonObjectRequest(Method.DELETE, url, null, { response ->
            val status = response.optBoolean(Constants.STATUS_PROPERTY, Constants.ERROR)
            if (status == Constants.SUCCESS) {
                val jsonObject: String? = response.optJSONObject(Constants.PRODUCTS_PROPERTY)?.toString()
                if (jsonObject != null) {
                    val objectType = object : TypeToken<ProductEntity>(){}.type
                    val objectLiveData = Gson().fromJson<ProductEntity>(jsonObject,objectType)
                    callback(objectLiveData)
                }
            }
        },{
            it.printStackTrace()
        }) {

            override fun getHeaders(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["Content-Type"] = "application/json"
                return params
            }
        }

        ProductApplication.productAPI.addToRequestQueue(jsonObjectRequest)
    }

    fun editComment(idComment: String, idArticle: String, comment: String, token: String,
                    callback: (ProductEntity) -> Unit) {
        val url = Constants.URL + Constants.POST_COMMENT + idArticle + "/" + idComment

        val jsonParams = JSONObject()
        if (comment.isNotEmpty()) jsonParams.put(Constants.COMMENT_PARAM, comment)
        if (token.isNotEmpty()) jsonParams.put(Constants.TOKEN_PARAM, token)

        val jsonObjectRequest = object : JsonObjectRequest(Method.PUT, url, jsonParams, { response ->
            val status = response.optBoolean(Constants.STATUS_PROPERTY, Constants.ERROR)
            if (status == Constants.SUCCESS) {
                val jsonObject: String? = response.optJSONObject(Constants.PRODUCTS_PROPERTY)?.toString()
                if (jsonObject != null) {
                    val objectType = object : TypeToken<ProductEntity>(){}.type
                    val objectLiveData = Gson().fromJson<ProductEntity>(jsonObject,objectType)
                    callback(objectLiveData)
                }
            }
        },{
            it.printStackTrace()
        }) {

            override fun getHeaders(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["Content-Type"] = "application/json"
                return params
            }
        }

        ProductApplication.productAPI.addToRequestQueue(jsonObjectRequest)
    }

}