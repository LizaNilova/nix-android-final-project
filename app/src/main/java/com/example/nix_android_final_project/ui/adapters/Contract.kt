package com.example.nix_android_final_project.ui.adapters

import com.example.nix_android_final_project.core.entities.CoffeeTypes
import com.example.nix_android_final_project.core.entities.Resources
import com.example.nix_android_final_project.core.entities.Response
import com.example.nix_android_final_project.ui.views.MainActivity

interface Contract {

    interface View {

        fun showInfo(info: Resources)

        fun showMessage(message : Response)

        fun takeLastPayment(response: Response)
    }

    interface Presenter {

        fun attach(view: View)

        fun detach()
    }
}
