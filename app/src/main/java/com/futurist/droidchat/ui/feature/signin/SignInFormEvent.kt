package com.futurist.droidchat.ui.feature.signin

sealed interface SignInFormEvent {
    data class EmailChanged(val email: String) : SignInFormEvent
    data class PasswordChanged(val password: String) : SignInFormEvent
    object Submit : SignInFormEvent
}