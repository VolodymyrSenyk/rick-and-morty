package com.senyk.rickandmorty.di.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import data.network.CharacterNetworkRepository
import domain.characters.CharacterRepository
import javax.inject.Singleton

@Suppress("unused")
@InstallIn(SingletonComponent::class)
@Module(includes = [NetworkModule::class])
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindCharacterRepository(repository: CharacterNetworkRepository): CharacterRepository
}
