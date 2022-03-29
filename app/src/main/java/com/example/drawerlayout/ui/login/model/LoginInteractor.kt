package com.example.drawerlayout.ui.login.model

import android.util.Log
import com.android.volley.toolbox.JsonObjectRequest
import com.example.drawerlayout.ProductApplication
import com.example.drawerlayout.common.utils.Constants
import com.example.drawerlayout.common.entities.LoggedInUser
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

class LoginInteractor {

    fun login(username: String, password: String, callback: (LoggedInUser?, String?) -> Unit) {
        val url = Constants.URL + Constants.POST_LOGIN
        val jsonParams = JSONObject()
        if (username.isNotEmpty()) jsonParams.put(Constants.EMAIL_PARAM, username)
        if (password.isNotEmpty()) jsonParams.put(Constants.PASSWORD_PARAM, password)

        val jsonObjectRequest = object : JsonObjectRequest(Method.POST, url, jsonParams, { response ->
            val status = response.optBoolean(Constants.STATUS_PROPERTY, Constants.ERROR)
            if (status == Constants.SUCCESS) {
                val jsonObject: String? = response.optJSONObject(Constants.LOGIN_PROPERTY)?.toString()
                if (jsonObject != null) {
                    val objectType = object : TypeToken<LoggedInUser>(){}.type
                    val objectLiveData = Gson().fromJson<LoggedInUser>(jsonObject,objectType)
                    callback(objectLiveData, null)
                }
            } else {
                val err: String? = response.opt(Constants.ERROR_PROPERTY)?.toString()
                callback(null, err)
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
                return super.getBodyContentType()
            }
        }

        ProductApplication.productAPI.addToRequestQueue(jsonObjectRequest)
    }
}