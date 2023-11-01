package com.ps.movies.feature

sealed interface UiEvent {
    data object InitState : UiEvent
}
