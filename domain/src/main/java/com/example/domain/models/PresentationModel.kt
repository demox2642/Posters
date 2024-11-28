package com.example.domain.models

data class PresentationModel<T : Any>(
    val errorText: String? = null,
    val data: T? = null,
    val screenState: ScreenState,
)

enum class ScreenState {
    LOADING,
    RESULT,
    ERROR,
    DEFAULT,
}
