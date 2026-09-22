package com.futurist.droidchat.ui.feature.signup

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.futurist.droidchat.R
import com.futurist.droidchat.ui.components.PrimaryButton
import com.futurist.droidchat.ui.components.ProfilePictureOptionsModalBottomSheet
import com.futurist.droidchat.ui.components.ProfilePictureSelector
import com.futurist.droidchat.ui.components.SecundaryTextField
import com.futurist.droidchat.ui.theme.BackgroundGradient
import com.futurist.droidchat.ui.theme.DroidChatTheme
import kotlinx.coroutines.launch

@Composable
fun SignUpRoute(
    viewModel: SignUpViewModel = hiltViewModel(),
) {
    val formState = viewModel.formState


    SignUpScreen(
        formState = formState, onFormEvent = viewModel::onFormEvent
    )

    formState.apiErrorMessageResId?.let { resId ->
        AlertDialog(
            onDismissRequest = viewModel::errorMessageShow,
            confirmButton = {
                Button(
                    onClick = viewModel::errorMessageShow
                ) {
                    Text(stringResource(R.string.common_ok))
                }
            },
            title = {
                Text(stringResource(R.string.common_generic_error_title))
            },
            text = {
                Text(stringResource(resId), color = MaterialTheme.colorScheme.onSurface)
            },
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    formState: SignUpFormState, onFormEvent: (SignUpFormEvent) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .background(brush = BackgroundGradient)
            .verticalScroll(state = rememberScrollState()),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(56.dp))

            Image(
                painter = painterResource(R.drawable.logo), contentDescription = null
            )
            Spacer(modifier = Modifier.height(16.dp))
            Surface(
                modifier = Modifier.fillMaxSize(), shape = MaterialTheme.shapes.extraLarge.copy(
                    bottomStart = CornerSize(0.dp),
                    bottomEnd = CornerSize(0.dp),
                ), color = MaterialTheme.colorScheme.surface
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    ProfilePictureSelector(
                        imageUri = formState.profilePictureUri, modifier = Modifier.clickable {
                            onFormEvent(
                                SignUpFormEvent.OpenProfilePictureOptionsModalBottomSheet
                            )
                        }, isCompressingImage = formState.isCompressingImage
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    SecundaryTextField(
                        label = stringResource(R.string.feature_sign_up_first_name),
                        value = formState.firstName,
                        onValueChange = {
                            onFormEvent(SignUpFormEvent.FirstNameChanged(it))
                        },
                        errorText = formState.firstNameError?.let {
                            stringResource(
                                id = it, stringResource(R.string.feature_sign_up_first_name)
                            )
                        })
                    Spacer(modifier = Modifier.height(22.dp))
                    SecundaryTextField(
                        label = stringResource(R.string.feature_sign_up_last_name),
                        value = formState.lastName,
                        onValueChange = {
                            onFormEvent(
                                SignUpFormEvent.LastNameChanged(
                                    it,

                                    )
                            )
                        },
                        errorText = formState.lastNameError?.let {
                            stringResource(
                                id = it, stringResource(R.string.feature_sign_up_last_name)
                            )
                        })
                    Spacer(modifier = Modifier.height(22.dp))

                    SecundaryTextField(
                        label = stringResource(R.string.feature_sign_up_email),
                        value = formState.email,
                        onValueChange = {
                            onFormEvent(SignUpFormEvent.EmailChanged(it))
                        },
                        keyboardType = KeyboardType.Email,
                        errorText = formState.emailError?.let { stringResource(id = it) })
                    Spacer(modifier = Modifier.height(22.dp))



                    SecundaryTextField(
                        label = stringResource(R.string.feature_sign_up_password),
                        value = formState.password,
                        onValueChange = {
                            onFormEvent(SignUpFormEvent.PasswordChanged(it))
                        },
                        keyboardType = KeyboardType.Password,
                        extraText = formState.passwordExtraText?.let { stringResource(id = it) },
                        imeAction = ImeAction.Next,
                        errorText = formState.passwordError?.let { stringResource(id = it) })
                    Spacer(modifier = Modifier.height(22.dp))
                    SecundaryTextField(
                        label = stringResource(R.string.feature_sign_up_password_confirmation),
                        value = formState.password,
                        onValueChange = {
                            onFormEvent(SignUpFormEvent.PasswordConfirmationChanged(it))
                        },
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done,
                        extraText = formState.passwordExtraText?.let { stringResource(id = it) })
                    Spacer(modifier = Modifier.height(22.dp))
                    PrimaryButton(
                        text = stringResource(R.string.feature_sign_up_button), onClick = {
                            onFormEvent(SignUpFormEvent.Submit)
                        }, isLoading = formState.isLoading
                    )
                }
            }

            val sheetState = rememberModalBottomSheetState()
            val scope = rememberCoroutineScope()

            if (formState.isProfilePictureModalBottomSheetOpen) {
                ProfilePictureOptionsModalBottomSheet(
                    onPeatureSelected = {
                        onFormEvent(SignUpFormEvent.ProfilePhotoUriChanged(it))
                        scope.launch {
                            sheetState.hide()
                        }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                onFormEvent(SignUpFormEvent.CloseProfilePictureOptionsModalBottomSheet)
                            }
                        }
                    }, onDismissRequest = {
                        onFormEvent(SignUpFormEvent.CloseProfilePictureOptionsModalBottomSheet)
                    }, sheetState = sheetState
                )
            }
        }
    }
}

@Preview
@Composable
private fun SignUpScreenPreview() {
    DroidChatTheme {
        SignUpScreen(
            formState = SignUpFormState(), onFormEvent = {})
    }
}