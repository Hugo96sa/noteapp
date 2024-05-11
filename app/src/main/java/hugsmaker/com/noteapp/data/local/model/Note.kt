package hugsmaker.com.noteapp.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Represents a Note entity to be stored in a Room database.
 *
 * @param id The unique identifier for the note. Generated automatically if not provided.
 * @param title The title of the note.
 * @param content The content or body of the note.
 * @param createdDate The date when the note was created.
 * @param isBookMarked Indicates if the note is bookmarked. Default is false.
 */
@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val content: String,
    val createdDate: Date,
    val isBookMarked: Boolean = false,
)
