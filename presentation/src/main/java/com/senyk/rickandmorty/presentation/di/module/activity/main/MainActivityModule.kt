package com.senyk.rickandmorty.presentation.di.module.activity.main

import com.senyk.rickandmorty.presentation.di.annotation.scope.ActivityScope
import com.senyk.rickandmorty.presentation.presentation.feature.main.MainActivity
import dagger.Binds
import dagger.Module
import dagger.android.support.DaggerAppCompatActivity

@Module
interface MainActivityModule {

    @ActivityScope
    @Binds
    fun bindActivity(activity: MainActivity): DaggerAppCompatActivity
}
