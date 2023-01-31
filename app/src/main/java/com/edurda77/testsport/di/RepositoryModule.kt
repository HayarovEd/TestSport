package com.edurda77.testsport.di

import com.edurda77.testsport.data.repository.SportLocalRepositoryImpl
import com.edurda77.testsport.domain.repository.SportLocalRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindLocalRepository(
        sportLocalRepositoryImpl: SportLocalRepositoryImpl
    ): SportLocalRepository
}