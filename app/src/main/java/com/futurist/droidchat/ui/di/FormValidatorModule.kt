package com.futurist.droidchat.ui.di

import com.futurist.droidchat.ui.feature.signup.SignUpFormState
import com.futurist.droidchat.ui.feature.signup.SignUpFormValidator
import com.futurist.droidchat.ui.validator.FormValidator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface FormValidatorModule {

    @Binds
    fun bindSignUpFormValidator(signUpFormValidator: SignUpFormValidator): FormValidator<SignUpFormState>
}