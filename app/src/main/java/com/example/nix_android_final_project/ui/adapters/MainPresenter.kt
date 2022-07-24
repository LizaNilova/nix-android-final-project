package com.example.nix_android_final_project.ui.adapters

import com.example.nix_android_final_project.core.entities.*
import com.example.nix_android_final_project.core.interactors.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainPresenter(
    private val makeSomeCoffeeInteractor: MakeSomeCoffeeInteractor,
    private val fillResourcesInteractor: FillResourcesInteractor,
    private val takeMoneyInteractor: TakeMoneyInteractor,
    private val getCoffeeMachineInfoInteractor: GetCoffeeMachineInfoInteractor,
    private val exchangeCurrencyInteractor: ExchangeCurrencyInteractor
) : Contract.Presenter, CoroutineScope {

    private var view: Contract.View? = null

    override fun attach(view: Contract.View) {
        this.view = view
        onViewAttached()
    }

    override fun detach() {
        this.view = null
    }

    private val job = Job()
    override val coroutineContext: CoroutineContext = job + Dispatchers.Default

    fun showInfoModel() {
        view?.showInfo(getCoffeeMachineInfoInteractor.invoke())
    }

    private fun onViewAttached() {
        showInfoModel()
    }

    fun buyCoffee(type: CoffeeTypes) : Response{
        return makeSomeCoffeeInteractor.invoke(type)
    }

    fun takeMoneyFromCoffeeMachine(): Response {
        return takeMoneyInteractor.invoke()
    }

    fun fillResources(resources: Resources?): Response {
        return if (resources != null) {
            fillResourcesInteractor.invoke(resources)
        } else {
            Response("Something wrong...")
        }
    }

    fun exchangePayment(payment: Payment) {
        launch {
            val response = exchangeCurrencyInteractor(payment)
            withContext(Dispatchers.Main) {
                view?.showMessage(response)
            }
        }
    }
}
