package com.senyk.rickandmorty

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.senyk.rickandmorty.components.SplashScreen
import com.senyk.rickandmorty.navigation.RickAndMortyNavHost
import core.ui.theme.RickAndMortyTheme
import core.ui.utils.isUiTestRunning
import core.ui.utils.setSystemBarsColors
import dagger.hilt.android.AndroidEntryPoint
import feature.settings.presentation.viewmodel.SettingsViewModel
import feature.splash.presentation.viewmodel.SplashViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            MainActivityScreen(splashScreen)
        }
    }
}

@Composable
private fun MainActivityScreen(splashScreen: SplashScreen) {
    val splashViewModel: SplashViewModel = hiltViewModel()
    val isSplashVisible by rememberSplashState(splashViewModel)
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.R) {
        splashScreen.setKeepOnScreenCondition { isSplashVisible }
    } else if (isSplashVisible) {
        SplashScreen()
    }

    val settingsViewModel: SettingsViewModel = hiltViewModel()
    val themeMode by settingsViewModel.themeModeFlow.collectAsState()
    themeMode?.let { mode ->
        splashViewModel.requirementDone(SplashViewModel.Requirement.THEME_LOADED)
        RickAndMortyTheme(themeMode = mode) {
            setSystemBarsColors(isSplashVisible)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primary)
                    .navigationBarsPadding()
            ) {
                RickAndMortyNavHost(rootNavController = rememberNavController())
            }
        }
    }
}

@Composable
private fun rememberSplashState(splashViewModel: SplashViewModel): State<Boolean> {
    val context = LocalContext.current
    return if (context.isUiTestRunning()) {
        remember { mutableStateOf(false) }
    } else {
        splashViewModel.show.collectAsStateWithLifecycle()
    }
}

@SuppressLint("ComposableNaming")
@Suppress("DEPRECATION")
@Composable
internal fun setSystemBarsColors(isSplashVisible: Boolean) {
    val splashScreenColor = colorResource(R.color.colorSplashScreen)
    setSystemBarsColors(
        statusBarColor = if (isSplashVisible) splashScreenColor else MaterialTheme.colorScheme.primary,
        navBarColor = if (isSplashVisible) splashScreenColor else Color.Black,
        isLight = isSplashVisible,
    )
}
