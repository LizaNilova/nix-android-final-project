package com.example.nix_android_final_project.core.interactors

import com.example.nix_android_final_project.core.entities.Resources
import com.example.nix_android_final_project.core.entities.Response
import com.example.nix_android_final_project.data.repositories.ActionRepository
import com.example.nix_android_final_project.data.repositories.FakeRepositoryImplementation

class FillResourcesInteractor (private val repository : ActionRepository) {

    operator fun invoke(resources: Resources) : Response = repository.fillResources(resources)
}
