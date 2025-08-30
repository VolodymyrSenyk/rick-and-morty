package com.senyk.rickandmorty.presentation.di.module

import com.senyk.rickandmorty.data.repository.CharacterNetworkRepository
import com.senyk.rickandmorty.domain.repository.CharacterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Suppress("unused")
@InstallIn(SingletonComponent::class)
@Module(includes = [NetworkModule::class])
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindCharacterRepository(repository: CharacterNetworkRepository): CharacterRepository
}
