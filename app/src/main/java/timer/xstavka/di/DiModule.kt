package timer.xstavka.di

import android.app.Application
import androidx.room.Room
import timer.xstavka.data.local.NoteDataBase
import timer.xstavka.data.repository.SportRemoteRepositoryImpl
import timer.xstavka.domain.repository.SportRemoteRepository
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
    fun bindConfig(sportRemoteRepository: SportRemoteRepositoryImpl): SportRemoteRepository {
        sportRemoteRepository.initConfigs()
        return SportRemoteRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDataBase {
        return Room.databaseBuilder(
            app,
            NoteDataBase::class.java,
            "notedb.db"
        ).build()
    }

}