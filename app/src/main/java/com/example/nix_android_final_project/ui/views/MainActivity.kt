package com.example.nix_android_final_project.ui.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.nix_android_final_project.R
import com.example.nix_android_final_project.core.entities.*
import com.example.nix_android_final_project.core.interactors.*
import com.example.nix_android_final_project.data.database.Database
import com.example.nix_android_final_project.data.mappers.DatabasePaymentToPaymentMapper
import com.example.nix_android_final_project.data.mappers.NetworkPaymentToPaymentMapper
import com.example.nix_android_final_project.data.mappers.PaymentToDatabasePaymentMapper
import com.example.nix_android_final_project.data.network.Network
import com.example.nix_android_final_project.data.repositories.FakeRepositoryImplementation
import com.example.nix_android_final_project.data.repositories.PaymentRepositoryImplementation
import com.example.nix_android_final_project.ui.adapters.Contract
import com.example.nix_android_final_project.ui.adapters.MainPresenter

class MainActivity : AppCompatActivity(), Contract.View {
    private val presenter by lazy {
        val repository = PaymentRepositoryImplementation(
            Network.api,
            NetworkPaymentToPaymentMapper(),
            Database.provideDao(baseContext),
            DatabasePaymentToPaymentMapper(),
            PaymentToDatabasePaymentMapper()
        )

        MainPresenter(
            MakeSomeCoffeeInteractor(FakeRepositoryImplementation()),
            FillResourcesInteractor(FakeRepositoryImplementation()),
            TakeMoneyInteractor(FakeRepositoryImplementation()),
            GetCoffeeMachineInfoInteractor(FakeRepositoryImplementation()),
            ExchangeCurrencyInteractor(repository),
            LoadPaymentInteractor(repository)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.attach(this)
        presenter.loadPayment()
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
        orderCoffee(CoffeeTypes.ESPRESSO)
    }

    private fun orderCoffee(coffee : CoffeeTypes) {
        val items = arrayOf("UAH - Ukrainian hryvnia", "JPY - Japanese yen", "EUR - Euro", "KZT - Kazakh tenge")
        val builder = AlertDialog.Builder(this)
        with(builder)
        {
            setTitle("Choose Currency")
            val response = presenter.buyCoffee(coffee)
            if (response.answer != "Sorry, not enough water!"
                && response.answer != "Sorry, not enough milk!"
                && response.answer != "Sorry, not enough coffee beans!"
                && response.answer != "Sorry, not enough paper cups!") {
                setItems(items) { dialog, which ->
                    when(items[which]){
                        "UAH - Ukrainian hryvnia" -> exchangePayment("UAH", coffee)
                        "JPY - Japanese yen" -> exchangePayment("JPY", coffee)
                        "EUR - Euro" -> exchangePayment("EUR", coffee)
                        "KZT - Kazakh tenge" -> exchangePayment("KZT", coffee)
                        else -> showMessage(Response("Something went wrong..."))
                    }
                    showMessage(response)
                }
                setNegativeButton("CANCEL"){ _, _ ->
                    null
                }
                show()
            } else {
                showMessage(response)
            }
        }
    }

    private fun exchangePayment(str : String, coffee : CoffeeTypes){
        val payment = Payment(
            amount = coffee.cost.toFloat(),
            currency = str
        )
        presenter.exchangePayment(payment)

    }

    fun loadButtonClick(view: View){
        presenter.loadPayment()
    }

    override fun takeLastPayment(response: Response){
        val lastPaymentLabel = findViewById<TextView>(R.id.lastPaymentLabel)
        lastPaymentLabel.text = response.answer
    }

    fun makeCappuccinoButtonClick(view: View) {
        orderCoffee(CoffeeTypes.CAPPUCCINO)
    }

    fun makeLatteButtonClick(view: View) {
        orderCoffee(CoffeeTypes.LATTE)
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
