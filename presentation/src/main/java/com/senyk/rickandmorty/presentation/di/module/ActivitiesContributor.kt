package com.senyk.rickandmorty.presentation.di.module

import com.senyk.rickandmorty.presentation.di.annotation.scope.ActivityScope
import com.senyk.rickandmorty.presentation.di.module.activity.main.MainActivityFragmentsContributor
import com.senyk.rickandmorty.presentation.di.module.activity.main.MainActivityModule
import com.senyk.rickandmorty.presentation.di.module.activity.splash.SplashActivityModule
import com.senyk.rickandmorty.presentation.presentation.feature.main.MainActivity
import com.senyk.rickandmorty.presentation.presentation.feature.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [ViewModelModule::class])
interface ActivitiesContributor {

    @ActivityScope
    @ContributesAndroidInjector(
        modules = [SplashActivityModule::class]
    )
    fun contributeSplashActivity(): SplashActivity

    @ActivityScope
    @ContributesAndroidInjector(
        modules = [
            MainActivityModule::class,
            MainActivityFragmentsContributor::class
        ]
    )
    fun contributeMainActivity(): MainActivity
}
