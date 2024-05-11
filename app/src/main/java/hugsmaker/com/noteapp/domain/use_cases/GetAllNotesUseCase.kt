package hugsmaker.com.noteapp.domain.use_cases

import hugsmaker.com.noteapp.data.local.model.Note
import hugsmaker.com.noteapp.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for fetching all notes from the repository.
 * @param repository The repository interface for data operations.
 */
class GetAllNotesUseCase @Inject constructor(
    private val repository: Repository
) {
    /**
     * Invokes the use case to fetch all notes from the repository.
     * @return A flow of lists containing all notes.
     */
    operator fun invoke(): Flow<List<Note>> =
        repository.getAllNotes()
}
