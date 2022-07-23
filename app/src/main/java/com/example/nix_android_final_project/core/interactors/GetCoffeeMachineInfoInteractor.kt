package com.example.nix_android_final_project.core.interactors

import com.example.nix_android_final_project.core.entities.Resources
import com.example.nix_android_final_project.data.repositories.ActionRepository
import com.example.nix_android_final_project.data.repositories.FakeRepositoryImplementation

class GetCoffeeMachineInfoInteractor (private val repository : ActionRepository) {
    operator fun invoke() : Resources = repository.getCurrentResources()
}