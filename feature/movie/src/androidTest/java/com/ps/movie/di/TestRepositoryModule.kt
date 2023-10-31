package com.ps.movie.di

import com.ps.data.remote.MovieService
import com.ps.data.repository.MovieRepositoryImpl
import com.ps.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object TestRepositoryModule {
    @Provides
    fun provideMovieRepository(movieService: MovieService): MovieRepository {
        return MovieRepositoryImpl(movieService)
    }
}
