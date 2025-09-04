package com.senyk.rickandmorty.di.module

import arch.util.PaginationHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Suppress("unused")
@InstallIn(ViewModelComponent::class)
@Module
class UtilsModule {

    @Provides
    fun getPaginationHelper(): PaginationHelper =
        PaginationHelper(
            dataSetSize = PAGE_SIZE,
            loadMoreTriggerDataSetSize = PAGE_SIZE / 2,
        )

    companion object {
        private const val PAGE_SIZE = 20
    }
}
