package com.example.nix_android_final_project.core.entities

enum class CoffeeTypes(val water : Int, val milk : Int, val coffeeBeans : Int, val cost : Int) {
    ESPRESSO(250, 0, 16, 4),
    CAPPUCCINO(200, 100, 12, 6),
    LATTE(350, 75, 20, 7)
}
