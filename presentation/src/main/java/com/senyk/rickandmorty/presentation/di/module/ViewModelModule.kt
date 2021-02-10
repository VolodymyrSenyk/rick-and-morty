package com.senyk.rickandmorty.presentation.di.module

import androidx.lifecycle.ViewModel
import com.senyk.rickandmorty.presentation.di.annotation.mapkey.ViewModelKey
import com.senyk.rickandmorty.presentation.presentation.feature.main.CharacterDetailsViewModel
import com.senyk.rickandmorty.presentation.presentation.feature.main.CharactersListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [RepositoryModule::class])
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CharactersListViewModel::class)
    fun bindCharactersListViewModel(viewModel: CharactersListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CharacterDetailsViewModel::class)
    fun bindCharacterDetailsViewModel(viewModel: CharacterDetailsViewModel): ViewModel
}
