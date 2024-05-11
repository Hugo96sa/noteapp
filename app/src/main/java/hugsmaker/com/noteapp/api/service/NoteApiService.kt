package hugsmaker.com.noteapp.api.service

import hugsmaker.com.noteapp.data.local.model.Note
import retrofit2.http.GET

interface NoteApiService {
    @GET("notes")
    suspend fun getNotes(): List<Note> // Assuming Note is your data model class
}
