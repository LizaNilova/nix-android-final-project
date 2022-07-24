package com.example.nix_android_final_project.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "payments")
data class DatabasePayment (
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    @ColumnInfo(name = "column_amount") val amount: Float,
    @ColumnInfo(name = "column_currency") val currency: String
)
