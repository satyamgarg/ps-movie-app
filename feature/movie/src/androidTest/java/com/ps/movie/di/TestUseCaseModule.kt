package com.ps.movie.di

import com.ps.data.repository.MovieRepositoryImpl
import com.ps.domain.usecase.MovieDetailsUseCase
import com.ps.domain.usecase.MovieListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object TestUseCaseModule {
    @Provides
    fun provideMovieListUseCase(movieRepository: MovieRepositoryImpl): MovieListUseCase {
        return MovieListUseCase(movieRepository)
    }

    @Provides
    fun provideMovieDetailsUseCase(movieRepository: MovieRepositoryImpl): MovieDetailsUseCase {
        return MovieDetailsUseCase(movieRepository)
    }
}
