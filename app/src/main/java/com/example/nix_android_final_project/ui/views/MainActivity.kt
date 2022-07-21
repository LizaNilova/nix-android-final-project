package com.example.nix_android_final_project.ui.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.nix_android_final_project.R
import com.example.nix_android_final_project.core.CoffeeMachineService
import com.example.nix_android_final_project.core.entities.CoffeeTypes
import com.example.nix_android_final_project.core.entities.Resources
import com.example.nix_android_final_project.core.entities.Response
import com.example.nix_android_final_project.ui.adapters.Contract
import com.example.nix_android_final_project.ui.adapters.MainPresenter


class MainActivity : AppCompatActivity(), Contract.View {
    override var presenter = MainPresenter(CoffeeMachineService())

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
        presenter.takeCommand(Response("take"))
    }

    fun fillResourcesButtonClick(view: View) {
        presenter.takeCommand(Response("fill"))
    }

    fun showCurrentResourcesButtonClick(view: View) {
        presenter.showInfoModel()
    }

    override fun showMessage(message : Response) {
        Toast.makeText(applicationContext,
            message.answer,
            Toast.LENGTH_SHORT).show()
    }

    override fun enterResourcesToFill() : Resources {
        val inputWater = findViewById<EditText>(R.id.fillWaterInput)
        val inputMilk = findViewById<EditText>(R.id.fillMilkInput)
        val inputCoffeeBeans = findViewById<EditText>(R.id.fillCoffeeBeansInput)
        val inputPaperCups = findViewById<EditText>(R.id.fillCupsInput)
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
        return Resources(
            inputWater.text.toString().toInt(),
            inputMilk.text.toString().toInt(),
            inputCoffeeBeans.text.toString().toInt(),
            inputPaperCups.text.toString().toInt(),
            0)
    }
}
