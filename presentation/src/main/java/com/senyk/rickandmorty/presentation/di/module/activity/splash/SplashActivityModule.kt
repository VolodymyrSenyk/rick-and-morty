package com.senyk.rickandmorty.presentation.di.module.activity.splash

import com.senyk.rickandmorty.presentation.di.annotation.scope.ActivityScope
import com.senyk.rickandmorty.presentation.presentation.feature.splash.SplashActivity
import dagger.Binds
import dagger.Module
import dagger.android.support.DaggerAppCompatActivity

@Module
interface SplashActivityModule {

    @ActivityScope
    @Binds
    fun bindActivity(activity: SplashActivity): DaggerAppCompatActivity
}
