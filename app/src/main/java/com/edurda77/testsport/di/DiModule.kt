package com.edurda77.testsport.di

import android.app.Application
import androidx.room.Room
import com.edurda77.testsport.data.local.NoteDataBase
import com.edurda77.testsport.data.repository.SportRepositoryImpl
import com.edurda77.testsport.domain.repository.SportRepository
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DiModule {

    @Provides
    @Singleton
    fun providesRemoteService(): FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

    @Provides
    @Singleton
    fun provideCryptoDatabase(app: Application): NoteDataBase {
        return Room.databaseBuilder(
            app,
            NoteDataBase::class.java,
            "notedb.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideRepository(sportRepositoryImpl: SportRepositoryImpl): SportRepository =
        sportRepositoryImpl

}