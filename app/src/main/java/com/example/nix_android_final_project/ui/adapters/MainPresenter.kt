package com.example.nix_android_final_project.ui.adapters

import com.example.nix_android_final_project.core.entities.CoffeeTypes
import com.example.nix_android_final_project.core.entities.Resources
import com.example.nix_android_final_project.core.entities.Response
import com.example.nix_android_final_project.core.interactors.FillResourcesInteractor
import com.example.nix_android_final_project.core.interactors.GetCoffeeMachineInfoInteractor
import com.example.nix_android_final_project.core.interactors.MakeSomeCoffeeInteractor
import com.example.nix_android_final_project.core.interactors.TakeMoneyInteractor

class MainPresenter(
    private val makeSomeCoffeeInteractor: MakeSomeCoffeeInteractor,
    private val fillResourcesInteractor: FillResourcesInteractor,
    private val takeMoneyInteractor: TakeMoneyInteractor,
    private val getCoffeeMachineInfoInteractor: GetCoffeeMachineInfoInteractor
) : Contract.Presenter {

    private var view: Contract.View? = null

    override fun attach(view: Contract.View) {
        this.view = view
        onViewAttached()
    }

    override fun detach() {
        this.view = null
    }

    fun showInfoModel() {
        view?.showInfo(getCoffeeMachineInfoInteractor.invoke())
    }

    private fun onViewAttached() {
        showInfoModel()
    }

    fun buyCoffee(type: CoffeeTypes) {
        view?.showMessage(makeSomeCoffeeInteractor.invoke(type))
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
}
