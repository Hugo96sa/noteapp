package hugsmaker.com.noteapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
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

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllNotesUseCase: GetAllNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
) : ViewModel() {
    private val _state: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        getAllNotes()
    }

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

    fun deleteNote(noteId: Long) = viewModelScope.launch {
        deleteNoteUseCase(noteId)
    }

    fun onBookMarkChange(note: Note) {
        viewModelScope.launch {
            updateNoteUseCase(note.copy(isBookMarked = !note.isBookMarked))
        }
    }
}

data class HomeState(
    val notes: ScreenViewState<List<Note>> = ScreenViewState.Loading,
)
