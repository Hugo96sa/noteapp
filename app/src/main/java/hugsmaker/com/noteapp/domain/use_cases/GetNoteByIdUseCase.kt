package hugsmaker.com.noteapp.domain.use_cases

import hugsmaker.com.noteapp.domain.repository.Repository
import javax.inject.Inject

class GetNoteByIdUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(id: Long) = repository.getNoteById(id)
}
