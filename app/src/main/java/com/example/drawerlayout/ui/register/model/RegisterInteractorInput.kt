package com.example.drawerlayout.ui.register.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.volley.toolbox.JsonObjectRequest
import com.example.drawerlayout.ProductApplication
import com.example.drawerlayout.common.entities.ErrorDetails
import com.example.drawerlayout.common.entities.ErrorRegister
import com.example.drawerlayout.common.entities.LoggedInUser
import com.example.drawerlayout.common.entities.RegisterInUser
import com.example.drawerlayout.common.utils.Constants
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

class RegisterInteractorInput {

    fun register(username: String, password: String , lastname: String, photoUri: String,
                 callback: (RegisterInUser?, ErrorDetails?) -> Unit) {

        val url = Constants.URL + Constants.POST_REGISTER
        val jsonParams = JSONObject()
        if (username.isNotEmpty()) jsonParams.put(Constants.EMAIL_PARAM, username)
        if (password.isNotEmpty()) jsonParams.put(Constants.PASSWORD_PARAM, password)
        if (lastname.isNotEmpty()) jsonParams.put(Constants.USERNAME_PARAM, lastname)
        if (photoUri != "null") {
            if (photoUri.isNotEmpty()) jsonParams.put(Constants.PHOTO_PARAM, photoUri)
        }

        val jsonObjectRequest = object : JsonObjectRequest(Method.POST, url, jsonParams, { response ->
            val status = response.optBoolean(Constants.STATUS_PROPERTY, Constants.ERROR)
            if (status == Constants.SUCCESS) {
                val jsonObject: String? = response.optJSONObject(Constants.LOGIN_PROPERTY)?.toString()
                if (jsonObject != null) {
                    val objectType = object : TypeToken<RegisterInUser>(){}.type
                    val objectLiveData = Gson().fromJson<RegisterInUser>(jsonObject,objectType)
                    Log.i("registro exitoso", objectLiveData.toString())
                    callback(objectLiveData, null)
                }
            } else {
                val err: String? = response.optJSONObject(Constants.ERROR_PROPERTY)?.toString()
                if (err != null) {
                    val objectType = object : TypeToken<ErrorDetails>(){}.type
                    val objectLiveData = Gson().fromJson<ErrorDetails>(err,objectType)
                    Log.i("registro exitoso", objectLiveData.toString())
                    callback(null, objectLiveData)
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

            override fun getBodyContentType(): String {
//                return if (postString != null) "application/json; charset=utf-8" else super.getBodyContentType()
                return super.getBodyContentType()
            }
        }

        ProductApplication.productAPI.addToRequestQueue(jsonObjectRequest)
    }
}