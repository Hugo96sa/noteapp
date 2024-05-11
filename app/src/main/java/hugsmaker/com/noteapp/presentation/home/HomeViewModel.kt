package hugsmaker.com.noteapp.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hugsmaker.com.noteapp.api.client.RetrofitClient
import hugsmaker.com.noteapp.common.ScreenViewState
import hugsmaker.com.noteapp.data.local.model.Note
import hugsmaker.com.noteapp.domain.use_cases.DeleteNoteUseCase
import hugsmaker.com.noteapp.domain.use_cases.GetAllNotesUseCase
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
 * ViewModel for managing data related to the home screen.
 * Handles interactions between the UI and data sources.
 *
 * @param getAllNotesUseCase Use case for retrieving all notes.
 * @param deleteNoteUseCase Use case for deleting a note.
 * @param updateNoteUseCase Use case for updating a note.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllNotesUseCase: GetAllNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
) : ViewModel() {
    private val _state: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    private val noteApiService = RetrofitClient.noteApiService
    private val _notes = MutableLiveData<List<Note>>()
    val notes: LiveData<List<Note>> get() = _notes

    /**
     * Initializes the ViewModel by fetching all notes.
     */
    init {
        getAllNotes()
    }

    /**
     * Fetches all notes from the data source and updates the state accordingly.
     */
    private fun getAllNotes() {
        getAllNotesUseCase()
            .onEach {
                _state.value = HomeState(notes = ScreenViewState.Success(it))
            }
            .catch {
                _state.value = HomeState(notes = ScreenViewState.Error(it.message))
            }
            .launchIn(viewModelScope)
    }

    /**
     * Deletes a note with the given ID.
     *
     * @param noteId The ID of the note to be deleted.
     */
    fun deleteNote(noteId: Long) = viewModelScope.launch {
        deleteNoteUseCase(noteId)
    }

    /**
     * Toggles the bookmark status of a note.
     *
     * @param note The note for which the bookmark status is to be toggled.
     */
    fun onBookMarkChange(note: Note) {
        viewModelScope.launch {
            updateNoteUseCase(note.copy(isBookMarked = !note.isBookMarked))
        }
    }

    /**
     * Fetches notes from the API using Retrofit.
     */
    fun fetchNotes() {
        viewModelScope.launch {
            try {
                val notesList = noteApiService.getNotes()
                _notes.value = notesList
            } catch (e: Exception) {
            }
        }
    }
}

/**
 * Represents the state of the home screen.
 *
 * @property notes The state of the notes list (loading, success, or error).
 */
data class HomeState(
    val notes: ScreenViewState<List<Note>> = ScreenViewState.Loading,
)
