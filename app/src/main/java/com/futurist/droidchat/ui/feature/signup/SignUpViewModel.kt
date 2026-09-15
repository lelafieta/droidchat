package com.futurist.droidchat.ui.feature.signup

import android.util.Log
import androidx.lifecycle.ViewModel
import com.futurist.droidchat.R
import com.futurist.droidchat.ui.validator.FormValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignUpViewModel(
    private val formValidator: FormValidator<SignUpFormState>
) : ViewModel() {
    private val _state = MutableStateFlow(SignUpFormState())
    val state = _state.asStateFlow()

    fun onFormEvent(event: SignUpFormEvent) {

        when (event) {
            is SignUpFormEvent.ProfilePhotoUriChanged -> {
                _state.update {
                    it.copy(profilePictureUri = event.uri)
                }
            }

            is SignUpFormEvent.FirstNameChanged -> {
                _state.update {
                    it.copy(firstName = event.firstName)
                }
            }

            is SignUpFormEvent.LastNameChanged -> {
                _state.update {
                    it.copy(lastName = event.lastName)
                }
            }

            is SignUpFormEvent.EmailChanged -> {
                _state.update {
                    it.copy(email = event.email)
                }
            }

            is SignUpFormEvent.PasswordChanged -> {
                _state.update {
                    it.copy(password = event.password)
                }
                updatePasswordExtraText()
            }

            is SignUpFormEvent.PasswordConfirmationChanged -> {
                _state.update {
                    it.copy(passwordConfirmation = event.passwordConfirmation)
                }
                updatePasswordExtraText()
            }

            SignUpFormEvent.OpenProfilePictureOptionsModalBottomSheet -> {
                _state.update {
                    it.copy(isProfilePictureModalBottomSheetOpen = true)
                }
            }

            SignUpFormEvent.CloseProfilePictureOptionsModalBottomSheet -> {
                _state.update {
                    it.copy(isProfilePictureModalBottomSheetOpen = false)
                }
            }

            SignUpFormEvent.Submit -> {
                doSignUp()
            }

        }

    }

    private fun updatePasswordExtraText() {
        _state.update {
            it.copy(
                passwordExtraText = if (it.password == it.passwordConfirmation) {
                    R.string.feature_sign_up_passwords_match
                } else null
            )
        }
    }

    private fun doSignUp() {
        Log.d("doSignUp", "doSignUp: ")
        if (isValidForm()) {
            _state.update {
                it.copy(isLoading = true)
            }
        }
    }

    private fun isValidForm(): Boolean {
        return !formValidator.validate(_state.value).also {
            _state.update {
                it
            }
        }.hasError;
    }

}
