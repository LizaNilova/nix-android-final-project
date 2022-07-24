package com.example.nix_android_final_project.data.repositories

import com.example.nix_android_final_project.core.entities.Payment

interface PaymentRepository {

    suspend fun makeNetworkExchange(payment: Payment): Payment

    fun savePayment(payment: Payment)

    fun loadPayment(): Payment?
}
