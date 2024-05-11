package hugsmaker.com.noteapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hugsmaker.com.noteapp.data.local.NoteDao
import hugsmaker.com.noteapp.data.local.NoteDatabase
import javax.inject.Singleton

/**
 * Dagger Hilt module for providing database-related dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    /**
     * Provides the NoteDao instance as a singleton.
     * @param database The NoteDatabase instance for accessing the DAO.
     * @return Singleton instance of NoteDao.
     */
    @Provides
    @Singleton
    fun provideNoteDao(database: NoteDatabase): NoteDao =
        database.noteDao

    /**
     * Provides the NoteDatabase instance as a singleton.
     * @param context The application context.
     * @return Singleton instance of NoteDatabase.
     */
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): NoteDatabase = Room.databaseBuilder(
        context,
        NoteDatabase::class.java,
        "notes_db"
    ).build()
}
