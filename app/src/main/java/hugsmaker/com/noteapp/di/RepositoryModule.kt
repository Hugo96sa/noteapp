package hugsmaker.com.noteapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hugsmaker.com.noteapp.data.repository.NoteRepositoryImpl
import hugsmaker.com.noteapp.domain.repository.Repository
import javax.inject.Singleton

/**
 * Dagger Hilt module for providing the Repository implementation.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    /**
     * Binds the Repository interface to its implementation, NoteRepositoryImpl.
     * @param repositoryImpl The implementation of Repository interface.
     * @return An instance of Repository.
     */
    @Binds
    @Singleton
    abstract fun bindRepository(repositoryImpl: NoteRepositoryImpl): Repository
}
