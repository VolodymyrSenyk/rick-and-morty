package com.senyk.rickandmorty.presentation.di.module.activity.main

import com.senyk.rickandmorty.presentation.di.annotation.scope.FragmentScope
import com.senyk.rickandmorty.presentation.presentation.feature.main.CharacterDetailsFragment
import com.senyk.rickandmorty.presentation.presentation.feature.main.CharactersListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface MainActivityFragmentsContributor {

    @FragmentScope
    @ContributesAndroidInjector
    fun contributeCharactersListFragment(): CharactersListFragment

    @FragmentScope
    @ContributesAndroidInjector
    fun contributeCharacterDetailsFragment(): CharacterDetailsFragment
}
