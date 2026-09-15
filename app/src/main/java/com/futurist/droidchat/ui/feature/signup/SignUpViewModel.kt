package com.futurist.droidchat.ui.feature.signup

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.futurist.droidchat.R
import com.futurist.droidchat.ui.validator.FormValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignUpViewModel(
    private val formValidator: FormValidator<SignUpFormState>
) : ViewModel() {
    var formState by mutableStateOf(SignUpFormState())
        private set


    fun onFormEvent(event: SignUpFormEvent) {

        when (event) {
            is SignUpFormEvent.ProfilePhotoUriChanged -> {
                formState = formState.copy(profilePictureUri = event.uri)
            }

            is SignUpFormEvent.FirstNameChanged -> {
                formState = formState.copy(firstName = event.firstName)
            }

            is SignUpFormEvent.LastNameChanged -> {
                formState = formState.copy(lastName = event.lastName)
            }

            is SignUpFormEvent.EmailChanged -> {
                formState = formState.copy(email = event.email)
            }

            is SignUpFormEvent.PasswordChanged -> {
                formState = formState.copy(password = event.password)
                updatePasswordExtraText()
            }

            is SignUpFormEvent.PasswordConfirmationChanged -> {
                formState = formState.copy(passwordConfirmation = event.passwordConfirmation)
                updatePasswordExtraText()
            }

            SignUpFormEvent.OpenProfilePictureOptionsModalBottomSheet -> {
                formState = formState.copy(isProfilePictureModalBottomSheetOpen = true)
            }

            SignUpFormEvent.CloseProfilePictureOptionsModalBottomSheet -> {
                formState = formState.copy(isProfilePictureModalBottomSheetOpen = false).also {
                    formValidator.validate(it)
                }
            }

            SignUpFormEvent.Submit -> {
                doSignUp()
            }

        }

    }

    private fun updatePasswordExtraText() {
        formState = formState.copy(
            passwordExtraText = if (formState.password.isNotEmpty() && formState.password != formState.passwordConfirmation) {
                R.string.error_message_password_confirmation_invalid
            } else null
        )
    }

    private fun doSignUp() {
        if (isValidForm()) {
            formState = formState.copy(isLoading = true)
        }
    }

    private fun isValidForm(): Boolean {
        return !formValidator.validate(formState).also {
            formState = it
        }.hasError;
    }

}
