package hugsmaker.com.noteapp.data.repository

import hugsmaker.com.noteapp.data.local.NoteDao
import hugsmaker.com.noteapp.data.local.model.Note
import hugsmaker.com.noteapp.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Implementation of the Repository interface for managing Note entities.
 * @param noteDao The data access object (DAO) for performing database operations on Note entities.
 */
class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao,
) : Repository {
    /**
     * Retrieves all notes from the database as a flow of lists of Note objects.
     */
    override fun getAllNotes(): Flow<List<Note>> {
        return noteDao.getAllNotes()
    }

    /**
     * Retrieves a specific note by its ID from the database as a flow of Note objects.
     * @param id The ID of the note to retrieve.
     */
    override fun getNoteById(id: Long): Flow<Note> {
        return noteDao.getNoteById(id)
    }

    /**
     * Inserts a new note into the database.
     * @param note The note object to insert.
     */
    override suspend fun insert(note: Note) {
        noteDao.insertNote(note)
    }

    /**
     * Updates an existing note in the database.
     * @param note The updated note object.
     */
    override suspend fun update(note: Note) {
        noteDao.update(note)
    }

    /**
     * Deletes a note from the database by its ID.
     * @param id The ID of the note to delete.
     */
    override suspend fun delete(id: Long) {
        noteDao.delete(id)
    }

    /**
     * Retrieves all bookmarked notes from the database as a flow of lists of Note objects.
     */
    override fun getBookMarkedNotes(): Flow<List<Note>> {
        return noteDao.getBookmarkedNotes()
    }
}
