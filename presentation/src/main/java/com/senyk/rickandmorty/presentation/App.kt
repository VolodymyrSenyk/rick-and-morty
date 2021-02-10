package com.senyk.rickandmorty.presentation

import com.senyk.rickandmorty.presentation.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class App : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.builder().context(applicationContext).build()
}
