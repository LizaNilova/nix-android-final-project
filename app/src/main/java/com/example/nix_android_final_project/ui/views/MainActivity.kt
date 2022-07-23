package com.example.nix_android_final_project.ui.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.nix_android_final_project.R
import com.example.nix_android_final_project.core.entities.CoffeeTypes
import com.example.nix_android_final_project.core.entities.Resources
import com.example.nix_android_final_project.core.entities.Response
import com.example.nix_android_final_project.core.interactors.FillResourcesInteractor
import com.example.nix_android_final_project.core.interactors.GetCoffeeMachineInfoInteractor
import com.example.nix_android_final_project.core.interactors.MakeSomeCoffeeInteractor
import com.example.nix_android_final_project.core.interactors.TakeMoneyInteractor
import com.example.nix_android_final_project.data.repositories.FakeRepositoryImplementation
import com.example.nix_android_final_project.ui.adapters.Contract
import com.example.nix_android_final_project.ui.adapters.MainPresenter

class MainActivity : AppCompatActivity(), Contract.View {
    override var presenter = MainPresenter(
        MakeSomeCoffeeInteractor(FakeRepositoryImplementation()),
        FillResourcesInteractor(FakeRepositoryImplementation()),
        TakeMoneyInteractor(FakeRepositoryImplementation()),
        GetCoffeeMachineInfoInteractor(FakeRepositoryImplementation())
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.attach(this)
    }

    override fun showInfo(info: Resources) {
        Toast.makeText(applicationContext, "The coffee machine has: \n" +
                "${info.water} ml of water\n" +
                "${info.milk} ml of milk\n" +
                "${info.coffeeBeans} g og coffee beans\n" +
                "${info.cups} of disposable cups\n" +
                "${info.money} of money\n",
            Toast.LENGTH_SHORT).show()
    }

    fun makeEspressoButtonClick(view: View) {
        presenter.buyCoffee(CoffeeTypes.ESPRESSO)
    }

    fun makeCappuccinoButtonClick(view: View) {
        presenter.buyCoffee(CoffeeTypes.CAPPUCCINO)
    }

    fun makeLatteButtonClick(view: View) {
        presenter.buyCoffee(CoffeeTypes.LATTE)
    }

    fun takeMoneyButtonClick(view: View) {
        showMessage(presenter.takeMoneyFromCoffeeMachine())
    }

    fun fillResourcesButtonClick(view: View) {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        builder.setTitle(R.string.fillResourcesLabel)
        val dialogLayout = inflater.inflate(R.layout.fill_resources_alert, null)
        val inputWater = dialogLayout.findViewById<EditText>(R.id.fillWaterInput)
        val inputMilk = dialogLayout.findViewById<EditText>(R.id.fillMilkInput)
        val inputCoffeeBeans = dialogLayout.findViewById<EditText>(R.id.fillCoffeeBeansInput)
        val inputPaperCups = dialogLayout.findViewById<EditText>(R.id.fillPaperCupsInput)
        if (inputWater.text.toString() == ""){
            inputWater.setText("0")
        }
        if (inputMilk.text.toString() == ""){
            inputMilk.setText("0")
        }
        if (inputCoffeeBeans.text.toString() == ""){
            inputCoffeeBeans.setText("0")
        }
        if (inputPaperCups.text.toString() == ""){
            inputPaperCups.setText("0")
        }
        builder.setView(dialogLayout)
        builder.setPositiveButton("OK") {
                _,
                _ -> showMessage(presenter.fillResources(Resources(
                        inputWater.text.toString().toInt(),
                        inputMilk.text.toString().toInt(),
                        inputCoffeeBeans.text.toString().toInt(),
                        inputPaperCups.text.toString().toInt(),
                        0
                )))
        }
        builder.show()
    }

    fun showCurrentResourcesButtonClick(view: View) {
        presenter.showInfoModel()
    }

    override fun showMessage(message : Response) {
        Toast.makeText(applicationContext,
            message.answer,
            Toast.LENGTH_SHORT).show()
    }
}
