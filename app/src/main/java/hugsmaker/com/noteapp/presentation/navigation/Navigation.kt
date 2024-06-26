package hugsmaker.com.noteapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import hugsmaker.com.noteapp.presentation.bookmark.BookmarkScreen
import hugsmaker.com.noteapp.presentation.bookmark.BookmarkViewModel
import hugsmaker.com.noteapp.presentation.detail.DetailAssistedFactory
import hugsmaker.com.noteapp.presentation.detail.DetailScreen
import hugsmaker.com.noteapp.presentation.home.HomeScreen
import hugsmaker.com.noteapp.presentation.home.HomeViewModel

/**
 * Enum defining the screens in the note navigation flow: Home, Detail, and Bookmark.
 */
enum class Screens {
    Home, Detail, Bookmark
}

/**
 * Composable function for handling navigation between different screens in the note app.
 *
 * @param modifier The modifier for the composable.
 * @param navHostController The NavHostController responsible for navigation.
 * @param homeViewModel The ViewModel for the home screen.
 * @param bookmarkViewModel The ViewModel for the bookmark screen.
 * @param assistedFactory The assisted factory for creating the DetailViewModel.
 */
@Composable
fun NoteNavigation(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    homeViewModel: HomeViewModel,
    bookmarkViewModel: BookmarkViewModel,
    assistedFactory: DetailAssistedFactory,
) {
    NavHost(
        navController = navHostController,
        startDestination = Screens.Home.name
    ) {
        composable(route = Screens.Home.name) {
            val state by homeViewModel.state.collectAsState()
            HomeScreen(
                state = state,
                onBookmarkChange = homeViewModel::onBookMarkChange,
                onDeleteNote = homeViewModel::deleteNote,
                onNoteClicked = {
                    navHostController.navigateToSingleTop(
                        route = "${Screens.Detail.name}?id=$it"
                    )
                }
            )
        }
        composable(route = Screens.Bookmark.name) {
            val state by bookmarkViewModel.state.collectAsState()
            BookmarkScreen(
                state = state,
                modifier = modifier,
                onBookMarkChange = bookmarkViewModel::onBookmarkChange,
                onDelete = bookmarkViewModel::deleteNote,
                onNoteClicked = {
                    navHostController.navigateToSingleTop(
                        route = "${Screens.Detail.name}?id=$it"
                    )
                }
            )
        }
        composable(
            route = "${Screens.Detail.name}?id={id}",
            arguments = listOf(
                navArgument("id") {
                    NavType.LongType
                    defaultValue = -1L
                }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id") ?: -1L
            DetailScreen(
                noteId = id,
                assistedFactory = assistedFactory,
                navigateUp = { navHostController.navigateUp() }
            )
        }
    }
}

/**
 * Extension function for NavHostController to navigate to a destination as a single top destination.
 *
 * @param route The destination route to navigate to.
 */
fun NavHostController.navigateToSingleTop(route: String) {
    navigate(route) {
        popUpTo(graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}
