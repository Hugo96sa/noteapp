package hugsmaker.com.noteapp.domain.use_cases

import hugsmaker.com.noteapp.domain.repository.Repository
import javax.inject.Inject

/**
 * Use case for deleting a note from the repository by ID.
 * @param repository The repository interface for data operations.
 */
class DeleteNoteUseCase @Inject constructor(
    private val repository: Repository,
) {
    /**
     * Invokes the use case to delete a note from the repository by ID.
     * @param id The ID of the note to delete.
     */
    suspend operator fun invoke(id: Long) = repository.delete(id)
}
