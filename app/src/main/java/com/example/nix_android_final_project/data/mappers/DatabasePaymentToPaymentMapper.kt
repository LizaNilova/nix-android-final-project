package com.example.nix_android_final_project.data.mappers

import com.example.nix_android_final_project.core.entities.Payment
import com.example.nix_android_final_project.data.database.DatabasePayment

class DatabasePaymentToPaymentMapper {

    fun toDomain(databasePayment: DatabasePayment): Payment = with(databasePayment) {
        Payment(
            id = id,
            currency = currency,
            amount = amount
        )
    }
}
