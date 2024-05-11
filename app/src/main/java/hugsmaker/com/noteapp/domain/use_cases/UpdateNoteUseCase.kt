package hugsmaker.com.noteapp.domain.use_cases

import hugsmaker.com.noteapp.data.local.model.Note
import hugsmaker.com.noteapp.domain.repository.Repository
import javax.inject.Inject

/**
 * Use case for updating a note in the repository.
 * @param repository The repository interface for data operations.
 */
class UpdateNoteUseCase @Inject constructor(
    private val repository: Repository
) {
    /**
     * Invokes the use case to update a note in the repository.
     * @param note The note object to be updated.
     */
    suspend operator fun invoke(note: Note) {
        repository.update(note)
    }
}
