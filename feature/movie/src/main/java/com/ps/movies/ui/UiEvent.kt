package com.ps.movies.ui

sealed interface UiEvent {
    data object InitState : UiEvent
}
