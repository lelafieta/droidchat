package com.futurist.droidchat.data.repository

import com.futurist.droidchat.data.di.IoDispatcher
import com.futurist.droidchat.data.network.NetworkDataSource
import com.futurist.droidchat.data.network.model.AuthRequest
import com.futurist.droidchat.data.network.model.CreateAccountRequest
import com.futurist.droidchat.model.CreateAccount
import com.futurist.droidchat.model.Image
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): AuthRepository {
    override suspend fun signUp(createAccount: CreateAccount) : Result<Unit>{
        return withContext(ioDispatcher){
            runCatching {
                networkDataSource.signUp(
                    request = CreateAccountRequest(
                        username = createAccount.username,
                        password = createAccount.password,
                        firstName = createAccount.firstName,
                        lastName = createAccount.lastName,
                        profilePicture = createAccount.profilePictureId
                    )
                )
            }
        }
    }

    override suspend fun signIn(username: String, password: String) {
        networkDataSource.signIn(
            request = AuthRequest(
                username = username,
                password = password
            )
        )
    }

    override suspend fun uploadProfilePicture(fileString: String): Result<Image> {
        return withContext(ioDispatcher){
            runCatching {
                val imageResponse = networkDataSource.uploadProfilePicture(fileString)
                Image(
                    id = imageResponse.id,
                    name = imageResponse.name,
                    type = imageResponse.type,
                    url = imageResponse.url
                )
            }
        }
    }
}