package com.example.nix_android_final_project.core.entities

import java.time.LocalDateTime

data class Response(val answer: String,
                    val time: String = LocalDateTime.now().toString()
)
