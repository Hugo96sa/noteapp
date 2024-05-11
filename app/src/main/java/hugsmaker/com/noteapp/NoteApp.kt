package hugsmaker.com.noteapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application class for the Note App, annotated with HiltAndroidApp for Hilt dependency injection.
 */
@HiltAndroidApp
class NoteApp : Application()
