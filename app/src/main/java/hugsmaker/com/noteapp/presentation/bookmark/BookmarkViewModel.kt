package hugsmaker.com.noteapp.presentation.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hugsmaker.com.noteapp.common.ScreenViewState
import hugsmaker.com.noteapp.data.local.model.Note
import hugsmaker.com.noteapp.domain.use_cases.DeleteNoteUseCase
import hugsmaker.com.noteapp.domain.use_cases.FilteredBookmarkNotes
import hugsmaker.com.noteapp.domain.use_cases.UpdateNoteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel class for managing bookmarked notes.
 * @param updateNoteUseCase Use case for updating a note.
 * @param filteredBookmarkNotes Use case for retrieving filtered bookmarked notes.
 * @param deleteNoteUseCase Use case for deleting a note.
 */
@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val filteredBookmarkNotes: FilteredBookmarkNotes,
    private val deleteNoteUseCase: DeleteNoteUseCase,
) : ViewModel() {
    private val _state: MutableStateFlow<BookmarkState> = MutableStateFlow(BookmarkState())
    val state: StateFlow<BookmarkState> = _state.asStateFlow()

    init {
        getBookMarkedNotes()
    }

    /**
     * Retrieves bookmarked notes and updates the state accordingly.
     */
    private fun getBookMarkedNotes() {
        filteredBookmarkNotes().onEach {
            _state.value = BookmarkState(
                notes = ScreenViewState.Success(it)
            )
        }
            .catch {
                _state.value = BookmarkState(notes = ScreenViewState.Error(it.message))
            }
            .launchIn(viewModelScope)
    }

    /**
     * Handles bookmark status changes for a note.
     * @param note The note to update bookmark status for.
     */
    fun onBookmarkChange(note: Note) {
        viewModelScope.launch {
            updateNoteUseCase(
                note.copy(
                    isBookMarked = !note.isBookMarked
                )
            )
        }
    }

    /**
     * Deletes a note.
     * @param noteId The ID of the note to delete.
     */
    fun deleteNote(noteId: Long) {
        viewModelScope.launch {
            deleteNoteUseCase(noteId)
        }
    }
}

/**
 * Represents the state of bookmarked notes.
 * @param notes The state of notes, including loading, success, or error.
 */
data class BookmarkState(
    val notes: ScreenViewState<List<Note>> = ScreenViewState.Loading,
)
