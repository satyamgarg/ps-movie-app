package com.ps.movie.di

import com.ps.domain.usecase.MovieDetailsUseCase
import com.ps.domain.usecase.MovieListUseCase
import com.ps.movie.feature.details.viewModel.MoviesDetailViewModel
import com.ps.movie.feature.list.viewModel.MoviesListViewModel
import com.ps.movie.util.DispatchersProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object TestViewModelModule {

    @Provides
    fun provideMoviesListViewModel(
        movieListUseCase: MovieListUseCase,
        dispatchersProvider: DispatchersProvider,
    ): MoviesListViewModel {
        return MoviesListViewModel(
            movieListUseCase = movieListUseCase,
            coroutineDispatcher = dispatchersProvider.io,
        )
    }

    @Provides
    fun provideMoviesDetailViewModel(
        movieDetailsUseCase: MovieDetailsUseCase,
        dispatchersProvider: DispatchersProvider,
    ): MoviesDetailViewModel {
        return MoviesDetailViewModel(
            movieDetailsUseCase = movieDetailsUseCase,
            coroutineDispatcher = dispatchersProvider.io,
        )
    }
}
