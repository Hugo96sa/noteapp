package hugsmaker.com.noteapp.domain.use_cases

import hugsmaker.com.noteapp.data.local.model.Note
import hugsmaker.com.noteapp.domain.repository.Repository
import javax.inject.Inject

/**
 * Use case for adding a new note to the repository.
 * @param repository The repository interface for data operations.
 */
class AddUseCase @Inject constructor(
    private val repository: Repository
) {
    /**
     * Invokes the use case to add a new note to the repository.
     * @param note The Note object to insert.
     */
    suspend operator fun invoke(note: Note) = repository.insert(note)
}
