package hugsmaker.com.noteapp.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkRemove
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.BookmarkAdd
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hugsmaker.com.noteapp.common.ScreenViewState
import hugsmaker.com.noteapp.data.local.model.Note
import java.util.Date

/**
 * Composable function for displaying the home screen with a list of notes.
 *
 * @param modifier The modifier for the composable.
 * @param state The state representing the home screen UI state.
 * @param onBookmarkChange Callback for handling bookmark changes.
 * @param onDeleteNote Callback for deleting a note.
 * @param onNoteClicked Callback for handling note click events.
 */
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeState,
    onBookmarkChange: (note: Note) -> Unit,
    onDeleteNote: (Long) -> Unit,
    onNoteClicked: (Long) -> Unit,
) {
    when (state.notes) {
        is ScreenViewState.Loading -> {
            CircularProgressIndicator()
        }

        is ScreenViewState.Success -> {
            val notes = state.notes.data
            HomeDetail(
                notes = notes,
                modifier = modifier,
                onBookmarkChange = onBookmarkChange,
                onDeleteNote = onDeleteNote,
                onNoteClicked = onNoteClicked
            )
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
 * Composable function for displaying the detailed view of notes in a staggered grid layout.
 *
 * @param notes The list of notes to display.
 * @param modifier The modifier for the composable.
 * @param onBookmarkChange Callback for handling bookmark changes.
 * @param onDeleteNote Callback for deleting a note.
 * @param onNoteClicked Callback for handling note click events.
 */
@Composable
private fun HomeDetail(
    notes: List<Note>,
    modifier: Modifier,
    onBookmarkChange: (note: Note) -> Unit,
    onDeleteNote: (Long) -> Unit,
    onNoteClicked: (Long) -> Unit,
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        contentPadding = PaddingValues(4.dp),
        modifier = modifier
    ) {
        itemsIndexed(notes) { index, note ->
            NoteCard(
                index = index,
                note = note,
                onBookmarkChange = onBookmarkChange,
                onDeleteNote = onDeleteNote,
                onNoteClicked = onNoteClicked
            )
        }
    }
}

/**
 * Composable function for displaying a note card with options like delete and bookmark.
 *
 * @param index The index of the note card.
 * @param note The note to display in the card.
 * @param onBookmarkChange Callback for handling bookmark changes.
 * @param onDeleteNote Callback for deleting a note.
 * @param onNoteClicked Callback for handling note click events.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteCard(
    index: Int,
    note: Note,
    onBookmarkChange: (note: Note) -> Unit,
    onDeleteNote: (Long) -> Unit,
    onNoteClicked: (Long) -> Unit,
) {
    val isEvenIndex = index % 2 == 0
    val roundValue = 20f
    val shape = when {
        isEvenIndex -> {
            RoundedCornerShape(
                topStart = roundValue,
                bottomEnd = roundValue
            )
        }

        else -> {
            RoundedCornerShape(
                topEnd = roundValue,
                bottomStart = roundValue
            )
        }
    }

    val icon = if (note.isBookMarked) Icons.Default.BookmarkRemove
    else Icons.Outlined.BookmarkAdd
    var showDeleteDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = shape,
        onClick = { onNoteClicked(note.id) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = note.title,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = note.content,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { showDeleteDialog = true }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                    )
                }
                IconButton(onClick = { onBookmarkChange(note) }) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                    )
                }
            }
        }
    }
    if (showDeleteDialog) {
        DeleteNoteDialog(
            onDeleteConfirmed = {
                onDeleteNote(note.id)
                showDeleteDialog = false
            },
            onDismiss = { showDeleteDialog = false }
        )
    }
}

/**
 * Composable function for displaying a delete note dialog.
 *
 * @param onDeleteConfirmed Callback for confirming note deletion.
 * @param onDismiss Callback for dismissing the dialog.
 */
@Composable
fun DeleteNoteDialog(
    onDeleteConfirmed: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Delete Note") },
        text = { Text("Are you sure you want to delete this note?") },
        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        onDeleteConfirmed()
                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(
                        Color(0xFFE57373) // Mellow red color
                    )
                ) {
                    Text("Delete", color = Color.White)
                }
                Button(
                    onClick = { onDismiss() },
                    colors = ButtonDefaults.buttonColors(
                        Color(0xFFE0E0E0) // Gray color
                    )
                ) {
                    Text("Cancel")
                }
            }
        },
        dismissButton = {
            // Empty dismiss button, since we handle dismissal in the Confirm button
        }
    )
}

/**
 * Preview function for the HomeScreen composable.
 */
@Preview(showSystemUi = true)
@Composable
fun PrevHome() {
    HomeScreen(
        state = HomeState(
            notes = ScreenViewState.Success(notes)
        ),
        onBookmarkChange = {},
        onDeleteNote = {},
        onNoteClicked = {}
    )
}

val placeHolderText =
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas porttitor nunc vel metus mollis suscipit. Phasellus nec eros id ex aliquam scelerisque. Phasellus quis feugiat eros. Nam sodales ante ac lorem convallis tempus. Sed lacinia consequat diam at ultrices. Nullam lacinia dignissim aliquam. Proin sit amet quam efficitur, euismod nunc eu, aliquam orci. Ut mattis orci a purus ultricies sodales. Pellentesque odio quam, aliquet nec accumsan et, pharetra et lacus. Pellentesque faucibus, dolor quis iaculis fringilla, ligula nisl imperdiet massa, vel volutpat velit elit ac magna. Interdum et malesuada fames ac ante ipsum primis in faucibus. Vivamus pharetra dolor nec magna condimentum volutpat. "

val notes = listOf(
    Note(
        title = "Room Database",
        content = placeHolderText + placeHolderText,
        createdDate = Date()
    ),
    Note(
        title = "JetPack Compose",
        content = "Testing",
        createdDate = Date(),
        isBookMarked = true,
    ),
    Note(
        title = "Room Database",
        content = placeHolderText + placeHolderText,
        createdDate = Date()
    ),
    Note(
        title = "JetPack Compose",
        content = placeHolderText,
        createdDate = Date(),
        isBookMarked = true,
    ),
    Note(
        title = "Room Database",
        content = placeHolderText,
        createdDate = Date()
    ),
    Note(
        title = "JetPack Compose",
        content = placeHolderText + placeHolderText,
        createdDate = Date(),
        isBookMarked = true,
    ),
)
