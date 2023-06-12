package com.gimnastiar.skinnyappbeta.ui.loginFragment

import androidx.lifecycle.ViewModel
import com.gimnastiar.skinnyappbeta.data.repository.LoginRepository

class LoginViewModel(private val repository: LoginRepository) : ViewModel() {

    fun login(
        username: String,
        password: String
    ) = repository.login(
        username, password
    )
}