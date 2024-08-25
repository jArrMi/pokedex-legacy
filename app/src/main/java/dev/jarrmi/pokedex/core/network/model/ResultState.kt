package dev.jarrmi.pokedex.core.network.model

sealed interface ResultState<out T> {
    val data: T?
        get() = when (this) {
            is Success -> data
            else -> null
        }

    data class Success<T>(override val data: T) : ResultState<T>

    data object Loading: ResultState<Nothing>

    data class Error(val details: Details) : ResultState<Nothing>
}

data class Details(
    val code: Int,
    val message: String?,
)
