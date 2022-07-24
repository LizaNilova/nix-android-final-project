package com.example.nix_android_final_project.data.mappers

import com.example.nix_android_final_project.core.entities.Payment
import com.example.nix_android_final_project.data.database.DatabasePayment

class PaymentToDatabasePaymentMapper {

    fun toData(payment: Payment): DatabasePayment = with(payment) {
        DatabasePayment(
            id = id,
            currency = currency,
            amount = amount
        )
    }
}
