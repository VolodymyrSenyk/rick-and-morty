package com.senyk.rickandmorty.presentation.presentation.feature.splash

import android.content.Intent
import android.os.Bundle
import com.senyk.rickandmorty.presentation.R
import com.senyk.rickandmorty.presentation.presentation.base.BaseActivity
import com.senyk.rickandmorty.presentation.presentation.feature.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.Timer
import java.util.TimerTask

@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    override val layoutRes = R.layout.activity_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timer().schedule(object : TimerTask() {
            override fun run() {
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
            }
        }, SPLASH_SCREEN_DELAY_IN_MILLIS)
    }

    companion object {
        private const val SPLASH_SCREEN_DELAY_IN_MILLIS = 3000L
    }
}
