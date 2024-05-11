package hugsmaker.com.noteapp.common

/**
 * Represents the state of a screen or UI component in an application.
 * It is a sealed class with three possible states: Loading, Success, and Error.
 *
 * @param T The type of data associated with the Success state.
 */
sealed class ScreenViewState<out T> {
    /**
     * Represents the loading state where data is being fetched or processed.
     * This state does not contain any specific data.
     */
    object Loading : ScreenViewState<Nothing>()

    /**
     * Represents the success state where data is successfully retrieved or processed.
     * @param data The data associated with the success state.
     */
    data class Success<T>(val data: T) : ScreenViewState<T>()

    /**
     * Represents the error state where an error occurred during data retrieval or processing.
     * @param message Optional error message providing additional context about the error.
     */
    data class Error(val message: String?) : ScreenViewState<Nothing>()
}
