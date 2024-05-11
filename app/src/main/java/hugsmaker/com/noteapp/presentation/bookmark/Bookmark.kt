package hugsmaker.com.noteapp.presentation.bookmark

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hugsmaker.com.noteapp.common.ScreenViewState
import hugsmaker.com.noteapp.data.local.model.Note
import hugsmaker.com.noteapp.presentation.home.NoteCard
import hugsmaker.com.noteapp.presentation.home.notes

/**
 * Composable function for displaying bookmarked notes.
 * @param state The state of bookmarked notes to be displayed.
 * @param modifier The modifier for styling and layout customization.
 * @param onBookMarkChange Callback function for handling bookmark status changes.
 * @param onDelete Callback function for deleting a note.
 * @param onNoteClicked Callback function for handling note clicks.
 */
@Composable
fun BookmarkScreen(
    state: BookmarkState,
    modifier: Modifier = Modifier,
    onBookMarkChange: (note: Note) -> Unit,
    onDelete: (Long) -> Unit,
    onNoteClicked: (Long) -> Unit,
) {
    when (state.notes) {
        is ScreenViewState.Loading -> {
            CircularProgressIndicator()
        }

        is ScreenViewState.Success -> {
            val notes = state.notes.data
            LazyColumn(
                contentPadding = PaddingValues(4.dp),
                modifier = modifier,
            ) {
                itemsIndexed(notes) { index: Int, note: Note ->
                    NoteCard(
                        index = index,
                        note = note,
                        onBookmarkChange = onBookMarkChange,
                        onDeleteNote = onDelete,
                        onNoteClicked = onNoteClicked
                    )
                }
            }
        }

        is ScreenViewState.Error -> {
            Text(
                text = state.notes.message ?: "Unknown Error",
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

/**
 * Preview function for the BookmarkScreen composable.
 * Used for testing and design preview purposes.
 */
@Preview(showSystemUi = true)
@Composable
fun PrevBookMark() {
    BookmarkScreen(
        state = BookmarkState(
            notes = ScreenViewState.Success(notes)
        ),
        onBookMarkChange = {},
        onDelete = {},
        onNoteClicked = {}
    )
}
