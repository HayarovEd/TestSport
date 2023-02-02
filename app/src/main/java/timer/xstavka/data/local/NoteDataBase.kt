package timer.xstavka.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import timer.xstavka.domain.model.Note

@Database(
    entities = [Note::class],
    version = 1
)
abstract class NoteDataBase: RoomDatabase() {
    abstract val dao: NoteDao
}