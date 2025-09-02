package com.senyk.rickandmorty.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import com.senyk.rickandmorty.R
import com.senyk.rickandmorty.setSystemBarsColors
import core.ui.utils.heightWithCoef

private const val FOREGROUND_IMAGE_HEIGHT_COEF = 0.6f

@Composable
fun SplashScreen(modifier: Modifier = Modifier) {
    setSystemBarsColors(isSplashVisible = true)
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(R.color.colorSplashScreen))
            .zIndex(1f)
    ) {
        Image(
            painter = painterResource(R.mipmap.ic_launcher_foreground),
            contentDescription = null,
            modifier = Modifier.size(heightWithCoef(FOREGROUND_IMAGE_HEIGHT_COEF))
        )
    }
}

@Preview
@Composable
private fun SplashScreenPreview() {
    SplashScreen()
}
