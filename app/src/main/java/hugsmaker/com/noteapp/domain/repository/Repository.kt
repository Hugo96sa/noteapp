package hugsmaker.com.noteapp.domain.repository

import hugsmaker.com.noteapp.data.local.model.Note
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface defining the operations for interacting with notes data.
 */
interface Repository {
    /**
     * Retrieves all notes as a flow of lists.
     * @return A flow emitting lists of Note objects.
     */
    fun getAllNotes(): Flow<List<Note>>

    /**
     * Retrieves a specific note by its ID as a flow.
     * @param id The ID of the note to retrieve.
     * @return A flow emitting the Note object with the specified ID.
     */
    fun getNoteById(id: Long): Flow<Note>

    /**
     * Inserts a new note into the database.
     * @param note The Note object to insert.
     */
    suspend fun insert(note: Note)

    /**
     * Updates an existing note in the database.
     * @param note The updated Note object.
     */
    suspend fun update(note: Note)

    /**
     * Deletes a note from the database by its ID.
     * @param id The ID of the note to delete.
     */
    suspend fun delete(id: Long)

    /**
     * Retrieves all bookmarked notes as a flow of lists.
     * @return A flow emitting lists of bookmarked Note objects.
     */
    fun getBookMarkedNotes(): Flow<List<Note>>
}
