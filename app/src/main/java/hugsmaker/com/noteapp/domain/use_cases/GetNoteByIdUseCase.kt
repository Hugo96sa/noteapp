package hugsmaker.com.noteapp.domain.use_cases

import hugsmaker.com.noteapp.domain.repository.Repository
import javax.inject.Inject

/**
 * Use case for fetching a note by its ID from the repository.
 * @param repository The repository interface for data operations.
 */
class GetNoteByIdUseCase @Inject constructor(
    private val repository: Repository
) {
    /**
     * Invokes the use case to fetch a note by its ID from the repository.
     * @param id The ID of the note to fetch.
     * @return A flow containing the note with the specified ID.
     */
    operator fun invoke(id: Long) = repository.getNoteById(id)
}
