package com.ps.domain.di

import com.ps.domain.repository.MovieRepository
import com.ps.domain.usecase.MovieDetailsUseCase
import com.ps.domain.usecase.MovieListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun provideMovieListUseCase(movieRepository: MovieRepository): MovieListUseCase {
        return MovieListUseCase(movieRepository)
    }

    @Provides
    fun provideMovieDetailsUseCase(movieRepository: MovieRepository): MovieDetailsUseCase {
        return MovieDetailsUseCase(movieRepository)
    }
}
