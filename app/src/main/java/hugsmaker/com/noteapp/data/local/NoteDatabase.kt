package hugsmaker.com.noteapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import hugsmaker.com.noteapp.data.local.NoteDao
import hugsmaker.com.noteapp.data.local.converters.DateConverter
import hugsmaker.com.noteapp.data.local.model.Note

/**
 * Room Database class for managing Note entities and providing access to the NoteDao.
 */
@TypeConverters(value = [DateConverter::class])
@Database(
    entities = [Note::class],
    version = 1,
    exportSchema = false
)
abstract class NoteDatabase : RoomDatabase() {
    /**
     * Provides access to the NoteDao interface for performing database operations on Note entities.
     */
    abstract val noteDao: NoteDao
}
