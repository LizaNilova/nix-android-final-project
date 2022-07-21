package com.example.nix_android_final_project.core

import com.example.nix_android_final_project.core.entities.CoffeeTypes
import com.example.nix_android_final_project.core.entities.Resources
import com.example.nix_android_final_project.core.entities.Response


class CoffeeMachineService (var water : Int = 400,
                            var milk : Int = 540,
                            var coffeeBeans : Int = 120,
                            var disposableCups: Int = 9,
                            private var money : Int = 550) {

    fun canIMakeThatMuchCoffee(countOfCups : Int) : Response {
        var enoughFlag = true
        if (this.water < (200 * countOfCups)
            || this.milk < (50 * countOfCups)
            || this.coffeeBeans < (15 * countOfCups)) {
            enoughFlag = false
        }
        return if (enoughFlag){
            if (this.water >= (200 * (countOfCups + 1))
                && this.milk >= (50 * (countOfCups + 1))
                && this.coffeeBeans >= (15 * (countOfCups + 1))) {
                Response("Yes, I can make that amount of coffee (and even ${minCountAdditionalCups(countOfCups)} more than that)")
            } else {
                Response("Yes, I can make that amount of coffee.")
            }
        } else {
            Response("No, I can make only ${minCountAdditionalCups(0)} cups of coffee")
        }
    }

    private fun minCountAdditionalCups(countOfCups: Int) : Int {
        var min = (this.water / 200) - countOfCups
        if (min >= ((this.milk / 50) - countOfCups)) {
            min = (this.milk / 50) - countOfCups
        }
        if (min >= ((this.coffeeBeans / 15) - countOfCups)) {
            min = (this.coffeeBeans / 15) - countOfCups
        }
        return min
    }

    fun makeSomeCoffee(coffee : CoffeeTypes) : Response {
            if (this.water < coffee.water) {
                return Response("Sorry, not enough water!")
            }
            if (this.milk < coffee.milk) {
                return Response("Sorry, not enough milk!")
            }
            if (this.coffeeBeans < coffee.coffeeBeans) {
                return Response("Sorry, not enough coffee beans!")
            }
            if (this.disposableCups < 1) {
                return Response("Sorry, not enough paper cups!")
            } else {
                this.water -= coffee.water
                this.milk -= coffee.milk
                this.coffeeBeans -= coffee.coffeeBeans
                this.disposableCups--
                this.money += coffee.cost
                return Response("I have enough resources, making you a coffee!")
            }
    }

    fun info() : Resources {
        return Resources(this.water, this.milk, this.coffeeBeans, this.disposableCups, this.money)
    }

    fun takeMoney() : Response {
        val temp = "I gave you ${this.money}"
        this.money = 0
        return Response(answer = temp)
    }

    fun fillResources(resources: Resources) {
        this.water += resources.water
        this.milk += resources.milk
        this.coffeeBeans += resources.coffeeBeans
        this.disposableCups += resources.cups
    }
}
