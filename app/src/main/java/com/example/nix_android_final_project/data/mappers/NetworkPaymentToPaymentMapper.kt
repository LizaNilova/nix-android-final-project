package com.example.nix_android_final_project.data.mappers

import com.example.nix_android_final_project.core.entities.Payment
import com.example.nix_android_final_project.data.network.NetworkPayment


class NetworkPaymentToPaymentMapper {

    fun toDomain(networkPayment: NetworkPayment): Payment = with(networkPayment) {
        Payment(
            currency = targetCurrency,
            amount = conversionResult.toFloat()
        )
    }
}
