package com.senyk.rickandmorty.di.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import data.datastore.SettingsDataStoreRepository
import data.network.CharactersNetworkRepository
import domain.characters.CharactersRepository
import domain.settings.SettingsRepository
import javax.inject.Singleton

@Suppress("unused")
@InstallIn(SingletonComponent::class)
@Module(includes = [NetworkModule::class])
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindCharactersRepository(repository: CharactersNetworkRepository): CharactersRepository

    @Binds
    @Singleton
    fun bindSettingsRepository(repository: SettingsDataStoreRepository): SettingsRepository
}
