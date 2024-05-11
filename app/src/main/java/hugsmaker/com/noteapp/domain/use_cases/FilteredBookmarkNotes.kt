package hugsmaker.com.noteapp.domain.use_cases

import hugsmaker.com.noteapp.data.local.model.Note
import hugsmaker.com.noteapp.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FilteredBookmarkNotes @Inject constructor(
    private val repository: Repository,
) {
    operator fun invoke(): Flow<List<Note>> {
        return repository.getBookMarkedNotes()
    }
}
