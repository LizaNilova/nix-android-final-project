package com.example.nix_android_final_project.core.interactors

import com.example.nix_android_final_project.core.entities.Data
import com.example.nix_android_final_project.core.entities.Response
import com.example.nix_android_final_project.data.repositories.PaymentRepository

class LoadPaymentInteractor(private val repository: PaymentRepository) {

    operator fun invoke(): Response {
        val payment = repository.loadPayment()
        return Response(answer = "Action saved in DB: ${payment?.amount ?: "None"} " +
                (payment?.currency ?: "Unknown")
        )
    }
}
