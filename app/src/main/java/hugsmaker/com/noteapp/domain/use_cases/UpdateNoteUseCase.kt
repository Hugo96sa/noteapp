package hugsmaker.com.noteapp.domain.use_cases

import hugsmaker.com.noteapp.data.local.model.Note
import hugsmaker.com.noteapp.domain.repository.Repository
import javax.inject.Inject

class UpdateNoteUseCase @Inject constructor(
    private val repository: Repository,
) {
    suspend operator fun invoke(note: Note) {
        repository.update(note)
    }
}
