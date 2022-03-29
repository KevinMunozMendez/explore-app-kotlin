package com.example.drawerlayout

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.drawerlayout.common.database.ApiService
import com.example.drawerlayout.common.database.ProductAPI
import com.example.drawerlayout.common.database.ProductDatabase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.properties.Delegates

class ProductApplication: Application() {
    companion object{
        lateinit var productAPI: ProductAPI
        lateinit var service: ApiService
        lateinit var database: ProductDatabase
    }

    override fun onCreate() {
        super.onCreate()

        val MIGRATION_1_2 = object : Migration(1,2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE cartEntity ADD COLUMN amount INT NOT NULL DEFAULT 0")
            }
        }

        database = Room.databaseBuilder(this,
            ProductDatabase::class.java,
            "productDatabase")
            .addMigrations(MIGRATION_1_2)
            .build()

        // Volley
        productAPI = ProductAPI.getInstance(this)


//        .addCallAdapterFactory(LiveDataCallAdapterFactory.create())
//        .addConverterFactory(LiveDataResponseBodyConverterFactory.create())
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://mytinerary-lopez-zaccaro.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(ApiService::class.java)
    }
}