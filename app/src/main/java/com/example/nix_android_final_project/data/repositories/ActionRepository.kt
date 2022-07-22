package com.example.nix_android_final_project.data.repositories


import com.example.nix_android_final_project.core.entities.CoffeeTypes
import com.example.nix_android_final_project.core.entities.Resources
import com.example.nix_android_final_project.core.entities.Response

interface ActionRepository {

    fun getCurrentResources() : Resources

    fun fillResources(resources : Resources) : Response

    fun buyCoffee(coffee : CoffeeTypes) : Response

    fun takeMoneyFromCoffeeMachine() : Response
}
