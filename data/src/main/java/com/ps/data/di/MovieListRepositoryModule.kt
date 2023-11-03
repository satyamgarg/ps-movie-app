package com.ps.data.di

import com.ps.data.repository.MovieListRepositoryImpl
import com.ps.domain.repository.MovieListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class MovieListRepositoryModule {

    @Binds
    abstract fun provideMovieRepository(movieListRepositoryImpl: MovieListRepositoryImpl): MovieListRepository
}
