package hugsmaker.com.noteapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import hugsmaker.com.noteapp.data.local.model.Note
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) interface for accessing and managing Note entities in a Room database.
 */
@Dao
interface NoteDao {
    /**
     * Retrieves all notes from the database ordered by their creation date.
     *
     * @return A Flow emitting a list of Note objects.
     */
    @Query("SELECT * FROM notes ORDER BY createdDate")
    fun getAllNotes(): Flow<List<Note>>

    /**
     * Retrieves a single note from the database by its ID.
     *
     * @param id The ID of the note to retrieve.
     * @return A Flow emitting a single Note object with the specified ID.
     */
    @Query("SELECT * FROM notes WHERE id=:id ORDER BY createdDate")
    fun getNoteById(id: Long): Flow<Note>

    /**
     * Inserts a new note into the database or replaces an existing note with the same ID.
     *
     * @param note The Note object to insert or update.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    /**
     * Updates an existing note in the database.
     *
     * @param note The updated Note object.
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(note: Note)

    /**
     * Deletes a note from the database by its ID.
     *
     * @param id The ID of the note to delete.
     */
    @Query("DELETE FROM notes WHERE id=:id")
    suspend fun delete(id: Long)

    /**
     * Retrieves all notes that are bookmarked from the database, ordered by their creation date in descending order.
     *
     * @return A Flow emitting a list of bookmarked Note objects.
     */
    @Query("SELECT * FROM notes WHERE isBookMarked = 1 ORDER BY createdDate DESC")
    fun getBookmarkedNotes(): Flow<List<Note>>
}
