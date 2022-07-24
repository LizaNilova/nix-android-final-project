package com.example.nix_android_final_project.data.repositories

import com.example.nix_android_final_project.core.entities.Payment
import com.example.nix_android_final_project.data.database.PaymentDao
import com.example.nix_android_final_project.data.mappers.DatabasePaymentToPaymentMapper
import com.example.nix_android_final_project.data.mappers.NetworkPaymentToPaymentMapper
import com.example.nix_android_final_project.data.mappers.PaymentToDatabasePaymentMapper
import com.example.nix_android_final_project.data.network.ExchangeServiceAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PaymentRepositoryImplementation(
    private val exchangeServiceApi: ExchangeServiceAPI,
    private val networkPaymentToPaymentMapper: NetworkPaymentToPaymentMapper,
    private val paymentDao: PaymentDao,
    private val databasePaymentToPaymentMapper: DatabasePaymentToPaymentMapper,
    private val paymentToDatabasePaymentMapper: PaymentToDatabasePaymentMapper
): PaymentRepository {

    override suspend fun makeNetworkExchange(payment: Payment): Payment {
        val networkPayment = withContext(Dispatchers.IO) {
            exchangeServiceApi.exchangeCurrency(
                "USD/${payment.currency}/${payment.amount}"
            )
        }
        return networkPaymentToPaymentMapper.toDomain(networkPayment)
    }

    override fun savePayment(payment: Payment) {
        val databasePayment = paymentToDatabasePaymentMapper.toData(payment)
        paymentDao.add(databasePayment)
    }

    override fun loadPayment(): Payment? {
        val databasePayment = paymentDao.getLastPayment()
        return databasePayment?.let {
            databasePaymentToPaymentMapper.toDomain(it)
        }
    }
}
