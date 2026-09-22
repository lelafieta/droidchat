package com.futurist.droidchat.ui.feature.signup

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.futurist.droidchat.R
import com.futurist.droidchat.data.repository.AuthRepository
import com.futurist.droidchat.model.CreateAccount
import com.futurist.droidchat.model.NetworkException
import com.futurist.droidchat.ui.validator.FormValidator
import com.futurist.droidchat.util.image.ImageCompressor
import com.futurist.droidchat.util.image.ImageCompressorImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val formValidator: FormValidator<SignUpFormState>,
    private val authRepository: AuthRepository,
    private val imageCompressor: ImageCompressor
) : ViewModel() {
    var formState by mutableStateOf(SignUpFormState())
        private set


    fun onFormEvent(event: SignUpFormEvent) {

        when (event) {
            is SignUpFormEvent.ProfilePhotoUriChanged -> {
                formState = formState.copy(profilePictureUri = event.uri)
                event.uri?.let {
                    compressImageAndUpdateState(it)
                }
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

    private fun compressImageAndUpdateState(uri: Uri){

        viewModelScope.launch {
            try {
                formState = formState.copy(isCompressingImage = true)
                val compressedFile = imageCompressor.compressAndResizeImage(uri)
                formState = formState.copy(isCompressingImage = false, profilePictureUri = compressedFile.toUri())

            } catch (e: Exception) {
                //
            } finally{
                formState = formState.copy(isCompressingImage = false)
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

            viewModelScope.launch {
                authRepository.signUp(
                    createAccount = CreateAccount(
                        firstName = formState.firstName,
                        lastName = formState.lastName,
                        password = formState.password,
                        username = formState.email,
                        profilePictureId = null
                    )
                ).fold(
                    onSuccess = {
                        formState = formState.copy(
                            isLoading = false,
                            isSignedUp = true
                        )
                    },
                    onFailure = {
                        formState = formState.copy(
                            isLoading = false,
                            apiErrorMessageResId = if (it is NetworkException.ApiException) {
                                when (it.statusCode) {
                                    400 -> R.string.error_message_api_form_validation_failed
                                    409 -> R.string.error_message_user_with_username_already_exists
                                    else -> R.string.common_generic_error_title
                                }
                            } else {
                                R.string.common_generic_error_title
                            }
                        )
                    }
                )
            }
        }
    }

    private fun isValidForm(): Boolean {
        return !formValidator.validate(formState).also {
            formState = it
        }.hasError;
    }

    fun errorMessageShow(){
        formState = formState.copy(apiErrorMessageResId = null)
    }

}
