package com.gimnastiar.skinnyappbeta.ui.registerFragment

import androidx.lifecycle.ViewModel
import com.gimnastiar.skinnyappbeta.data.repository.LoginRepository

class RegisterViewModel(private val repository: LoginRepository): ViewModel() {

    fun register(
        name: String,
        username: String,
        password: String
    ) = repository.register(
        name,
        username,
        password
    )

}