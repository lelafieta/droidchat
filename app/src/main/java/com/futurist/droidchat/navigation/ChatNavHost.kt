package com.futurist.droidchat.navigation

import android.widget.Toast
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.futurist.droidchat.navigation.extension.slideOutTo
import com.futurist.droidchat.ui.feature.signin.SignInRoute
import com.futurist.droidchat.ui.feature.signup.SignUpRoute
import com.futurist.droidchat.ui.feature.splash.SplashRoute
import kotlinx.serialization.Serializable


sealed interface Route{
    @Serializable
    object  SplashRoute

    @Serializable
    object SignInRoute

    @Serializable
    object SignUpRoute
}

@Composable
fun ChatNavHost(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Route.SplashRoute){

        composable<Route.SplashRoute>(

        ) {
            SplashRoute(
                onNavigateToSignIn = {
                    navController.navigate(

                        route = Route.SignInRoute,
                        navOptions = navOptions {
                            popUpTo(Route.SplashRoute) {
                                inclusive = true
                            }
                        }
                    )
                }
            )
        }
        composable<Route.SignInRoute>(
            enterTransition = {
                this.slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right)
            },
            exitTransition = {
                this.slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            }
        ) {
            val context = LocalContext.current
            SignInRoute(
                navigateToSignUp = {
                    navController.navigate(Route.SignUpRoute)
                },
                navigateToMain = {
                    Toast.makeText(
                        context,
                        "Navigate to main",
                        Toast.LENGTH_SHORT
                    ).show()


                }
            )
        }
        composable<Route.SignUpRoute>(
            enterTransition = {
                this.slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            },
            exitTransition = {
                this.slideOutTo(AnimatedContentTransitionScope.SlideDirection.Right)
            }
        ) {
            SignUpRoute(
                onSignUpSuccess = {
                    navController.popBackStack()
                }
            )
        }
    }
}