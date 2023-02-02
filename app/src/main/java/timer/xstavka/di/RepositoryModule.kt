package timer.xstavka.di

import timer.xstavka.data.repository.SportLocalRepositoryImpl
import timer.xstavka.domain.repository.SportLocalRepository
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