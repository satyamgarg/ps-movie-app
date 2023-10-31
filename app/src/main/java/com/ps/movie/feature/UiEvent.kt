package com.ps.movie.feature

sealed interface UiEvent {
    data object InitState : UiEvent
}
