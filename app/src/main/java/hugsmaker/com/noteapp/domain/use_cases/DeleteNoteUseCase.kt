package hugsmaker.com.noteapp.domain.use_cases

import hugsmaker.com.noteapp.domain.repository.Repository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
    private val repository: Repository,
) {
    suspend operator fun invoke(id: Long) = repository.delete(id)
}
