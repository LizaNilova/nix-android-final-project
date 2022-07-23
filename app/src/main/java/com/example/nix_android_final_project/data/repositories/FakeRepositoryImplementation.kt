package com.example.nix_android_final_project.data.repositories


import com.example.nix_android_final_project.core.entities.CoffeeTypes
import com.example.nix_android_final_project.core.entities.Resources
import com.example.nix_android_final_project.core.entities.Response

class FakeRepositoryImplementation : ActionRepository {
    object CoffeeMachine {
        var water = 400
        var milk = 540
        var coffeeBeans = 120
        var paperCups = 9
        var money = 550
    }
    override fun fillResources(resources: Resources) : Response{
        CoffeeMachine.water += resources.water
        CoffeeMachine.milk += resources.milk
        CoffeeMachine.coffeeBeans += resources.coffeeBeans
        CoffeeMachine.paperCups += resources.cups
        if (resources.water == 0
            && resources.milk == 0
            && resources.coffeeBeans == 0
            && resources.cups == 0){
            return Response("Nothing to fill!")
        }
        return Response("Successfully filled!")
}

    override fun getCurrentResources():  Resources =
        Resources(
            CoffeeMachine.water,
            CoffeeMachine.milk,
            CoffeeMachine.coffeeBeans,
            CoffeeMachine.paperCups,
            CoffeeMachine.money
        )

    override fun buyCoffee(coffee : CoffeeTypes) : Response {
        if (CoffeeMachine.water < coffee.water) {
            return Response("Sorry, not enough water!")
        }
        if (CoffeeMachine.milk < coffee.milk) {
            return Response("Sorry, not enough milk!")
        }
        if (CoffeeMachine.coffeeBeans < coffee.coffeeBeans) {
            return Response("Sorry, not enough coffee beans!")
        }
        return if (CoffeeMachine.paperCups < 1) {
            Response("Sorry, not enough paper cups!")
        } else {
            CoffeeMachine.water -= coffee.water
            CoffeeMachine.milk -= coffee.milk
            CoffeeMachine.coffeeBeans -= coffee.coffeeBeans
            CoffeeMachine.paperCups--
            CoffeeMachine.money += coffee.cost
            Response("I have enough resources, making you a coffee!")
        }
    }

    override fun takeMoneyFromCoffeeMachine() : Response{
        val temp = "I gave you ${CoffeeMachine.money}"
        CoffeeMachine.money = 0
        return Response(answer = temp, )
    }
}
