package com.ps.movie.di

import com.ps.movie.util.CoroutineDispatchers
import com.ps.movie.util.DispatchersProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object TestDispatcherModule {

    @Provides
    fun provideDispatcher(): DispatchersProvider {
        return CoroutineDispatchers()
    }
}
