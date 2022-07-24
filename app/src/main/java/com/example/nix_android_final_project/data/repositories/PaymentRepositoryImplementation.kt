package com.example.nix_android_final_project.data.repositories

import com.example.nix_android_final_project.core.entities.Payment
import com.example.nix_android_final_project.data.mappers.NetworkPaymentToPaymentMapper
import com.example.nix_android_final_project.data.network.ExchangeServiceAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PaymentRepositoryImplementation(
    private val exchangeServiceApi: ExchangeServiceAPI,
    private val networkPaymentToPaymentMapper: NetworkPaymentToPaymentMapper
): PaymentRepository {

    override suspend fun makeNetworkExchange(payment: Payment): Payment {
        val networkPayment = withContext(Dispatchers.IO) {
            exchangeServiceApi.exchangeCurrency(
                "USD/${payment.currency}/${payment.amount}"
            )
        }
        return networkPaymentToPaymentMapper.toDomain(networkPayment)
    }
}
