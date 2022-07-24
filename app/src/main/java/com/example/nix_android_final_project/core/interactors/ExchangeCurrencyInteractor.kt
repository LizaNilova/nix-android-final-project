package com.example.nix_android_final_project.core.interactors

import com.example.nix_android_final_project.core.entities.Payment
import com.example.nix_android_final_project.core.entities.Response
import com.example.nix_android_final_project.data.repositories.PaymentRepository

class ExchangeCurrencyInteractor(private val repository: PaymentRepository) {

    suspend operator fun invoke(payment: Payment): Response {
        val exchangedPayment = if (payment.currency != "USD") {
            repository.makeNetworkExchange(payment)
        } else {
            payment
        }

        return with(exchangedPayment) {
            //repository.savePayment(exchangedPayment)
            Response(
                answer = "Exchanged payment: $amount $currency"
            )
        }
    }
}
