package com.senyk.rickandmorty

import android.annotation.SuppressLint
import android.app.Activity
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
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
import dagger.hilt.android.AndroidEntryPoint
import feature.settings.viewmodel.SettingsViewModel
import feature.settings.viewmodel.SplashViewModel

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
    val settingsViewModel: SettingsViewModel = hiltViewModel()
    val themeMode by settingsViewModel.themeModeFlow.collectAsState()
    val context = LocalContext.current
    val isSplashVisible by if (context.isUiTestRunning()) {
        remember { mutableStateOf(false) }
    } else {
        splashViewModel.show.collectAsStateWithLifecycle()
    }
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.R) {
        splashScreen.setKeepOnScreenCondition { isSplashVisible }
    } else if (isSplashVisible) {
        SplashScreen()
    }

    themeMode?.let { mode ->
        splashViewModel.requirementDone(SplashViewModel.Requirement.THEME_LOADED)
        RickAndMortyTheme(themeMode = mode) {
            val statusBarColor = if (isSplashVisible) colorResource(R.color.colorSplashScreen) else MaterialTheme.colorScheme.primary
            val navigationBarColor = if (isSplashVisible) colorResource(R.color.colorSplashScreen) else Color.Black
            setSystemBarsColors(statusBarColor = statusBarColor, navBarColor = navigationBarColor, isLight = isSplashVisible)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(statusBarColor)
                    .navigationBarsPadding()
            ) {
                RickAndMortyNavHost(rootNavController = rememberNavController())
            }
        }
    }
}

@SuppressLint("ComposableNaming")
@Suppress("DEPRECATION")
@Composable
internal fun setSystemBarsColors(
    statusBarColor: Color,
    navBarColor: Color,
    isLight: Boolean,
) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.VANILLA_ICE_CREAM) {
        (LocalView.current.context as? Activity)?.window?.let { window ->
            window.statusBarColor = statusBarColor.toArgb()
            window.navigationBarColor = navBarColor.toArgb()
            WindowCompat.getInsetsController(window, LocalView.current).apply {
                isAppearanceLightStatusBars = isLight
                isAppearanceLightNavigationBars = isLight
            }
        }
    }
}
