package com.senyk.rickandmorty.presentation

import android.app.Application
import android.os.StrictMode
import arch.log.Kermit
import co.touchlab.kermit.Severity
import co.touchlab.kermit.StaticConfig
import co.touchlab.kermit.platformLogWriter
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        setUpStrictMode()
        setUpLoggingTool()
    }

    private fun setUpStrictMode() {
        if (!BuildConfig.DEBUG) return
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build()
        )
        StrictMode.setVmPolicy(
            StrictMode.VmPolicy.Builder()
                .detectAll()
                .build()
        )
    }

    private fun setUpLoggingTool() {
        Kermit.setUp(
            config = StaticConfig(
                minSeverity = if (BuildConfig.DEBUG) Severity.Verbose else Severity.Warn,
                logWriterList = listOf(platformLogWriter())
            ),
        )
    }
}
