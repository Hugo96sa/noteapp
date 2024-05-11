package hugsmaker.com.noteapp.presentation.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BookmarkRemove
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Update
import androidx.compose.material.icons.outlined.BookmarkAdd
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import hugsmaker.com.noteapp.ui.theme.pastelColors

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    noteId: Long,
    assistedFactory: DetailAssistedFactory,
    navigateUp: () -> Unit,
) {
    val viewModel = viewModel(
        modelClass = DetailViewModel::class.java,
        factory = DetailedViewModelFactory(
            noteId = noteId,
            assistedFactory = assistedFactory
        )
    )

    val state = viewModel.state
    DetailScreen(
        modifier = modifier,
        isUpdatingNote = state.isUpdatingNote,
        isFormNotBlank = viewModel.isFormNotBlank,
        title = state.title,
        content = state.content,
        isBookMark = state.isBookmark,
        onBookMarkChange = viewModel::onBookMarkChange,
        onContentChange = viewModel::onContentChange,
        onTitleChange = viewModel::onTitleChange,
        onBtnClick = {
            viewModel.addOrUpdateNote()
            navigateUp()
        },
        onNavigate = navigateUp
    )
}

@Composable
private fun DetailScreen(
    modifier: Modifier,
    isUpdatingNote: Boolean,
    title: String,
    content: String,
    isBookMark: Boolean,
    onBookMarkChange: (Boolean) -> Unit,
    isFormNotBlank: Boolean,
    onTitleChange: (String) -> Unit,
    onContentChange: (String) -> Unit,
    onBtnClick: () -> Unit,
    onNavigate: () -> Unit,
) {
    var showColorPicker by remember { mutableStateOf(false) }
    var selectedColor by remember { mutableStateOf(Color.White) }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopSection(
            title = title,
            isBookMark = isBookMark,
            onBookmarkChange = onBookMarkChange,
            onTitleChange = onTitleChange,
            onNavigate = onNavigate
        )
        Spacer(modifier = Modifier.size(12.dp))
        AnimatedVisibility(isFormNotBlank) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = onBtnClick) {
                    val icon = if (isUpdatingNote) Icons.Default.Update else Icons.Default.Check
                    Icon(imageVector = icon, contentDescription = null)
                }
                Spacer(modifier = Modifier.width(6.dp))
                IconButton(onClick = { showColorPicker = true }) {
                    Icon(imageVector = Icons.Default.Palette, contentDescription = "Color Picker")
                }
            }
        }
        Spacer(modifier = Modifier.size(12.dp))
        NotesTextField(
            modifier = Modifier.weight(1f),
            value = content,
            label = "Content",
            onValueChange = onContentChange
        )

        if (showColorPicker) {
            ColorPickerDialog(
                selectedColor = selectedColor,
                onColorSelected = { color ->
                    selectedColor = color
                    // You can handle the selected color here, such as updating the note with the color
                },
                onDismiss = { showColorPicker = false }
            )
        }
    }
}

@Composable
fun TopSection(
    modifier: Modifier = Modifier,
    title: String,
    isBookMark: Boolean,
    onBookmarkChange: (Boolean) -> Unit,
    onTitleChange: (String) -> Unit,
    onNavigate: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onNavigate) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null
            )
        }
        NotesTextField(
            modifier = Modifier.weight(2f),
            value = title,
            label = "Title",
            labelAlign = TextAlign.Center,
            onValueChange = onTitleChange
        )
        IconButton(onClick = { onBookmarkChange(!isBookMark) }) {
            val icon = if (isBookMark) Icons.Default.BookmarkRemove
            else Icons.Outlined.BookmarkAdd
            Icon(
                imageVector = icon,
                contentDescription = null
            )
        }
    }
}

@Composable
private fun NotesTextField(
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    labelAlign: TextAlign? = null,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        colors = TextFieldDefaults.colors(
            disabledContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
        placeholder = {
            Text(
                text = "Insert $label",
                textAlign = labelAlign,
                modifier = modifier.fillMaxWidth()
            )
        }
    )
}

@Composable
fun ColorPickerDialog(
    selectedColor: Color,
    onColorSelected: (Color) -> Unit,
    onDismiss: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.width(200.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            pastelColors.forEach { color ->
                ColorCircle(
                    color = color,
                    isSelected = color == selectedColor,
                    onColorSelected = {
                        onColorSelected(it)
                        onDismiss()
                    }
                )
            }
        }
    }
}

@Composable
fun ColorCircle(
    color: Color,
    isSelected: Boolean,
    onColorSelected: (Color) -> Unit
) {
    val size = 40.dp
    val selectedModifier = if (isSelected) Modifier.background(Color.Gray) else Modifier

    Box(
        modifier = Modifier
            .size(size)
            .clickable { onColorSelected(color) }
            .padding(4.dp)
            .then(selectedModifier),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(size - 8.dp)
                .background(color),
        )
    }
}

@Composable
fun PreviewColorPickerDialog() {
    ColorPickerDialog(
        selectedColor = Color.White,
        onColorSelected = {},
        onDismiss = {}
    )
}
