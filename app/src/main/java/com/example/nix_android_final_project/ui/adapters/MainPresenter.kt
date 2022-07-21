package com.example.nix_android_final_project.ui.adapters

import android.widget.Toast
import com.example.nix_android_final_project.core.CoffeeMachineService
import com.example.nix_android_final_project.core.entities.CoffeeTypes
import com.example.nix_android_final_project.core.entities.Data
import com.example.nix_android_final_project.core.entities.Resources
import com.example.nix_android_final_project.core.entities.Response

class MainPresenter(
    private val model: CoffeeMachineService
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
        view?.showInfo(model.info())
    }

    private fun onViewAttached() {
        showInfoModel()
    }

    fun buyCoffee(type: CoffeeTypes) {
        view?.showMessage(model.makeSomeCoffee(type))
    }

    fun takeCommand(com : Response) {
        when (com.answer) {
            "fill" -> view?.showMessage(fillResources(view?.enterResourcesToFill()))
            "take" -> view?.showMessage(model.takeMoney())
            else -> view?.showMessage(Response("Wrong name of command ..."))
        }
    }

    private fun fillResources(resources: Resources?) : Response {
        if (resources != null){
            model.fillResources(resources)
            if (resources.water == 0
                && resources.milk == 0
                && resources.coffeeBeans == 0
                && resources.cups == 0){
                return Response("Nothing to fill!")
            }
            return Response("Successfully filled!")
        }
        return Response("Something wrong...")
    }
}
