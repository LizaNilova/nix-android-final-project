package com.example.nix_android_final_project.data.database

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson

object Database {

    private const val DB_NAME = "cocktail_database"

    fun provideDao(context: Context): PaymentDao = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        DB_NAME
    )
        .build()
        .paymentDao()
}
