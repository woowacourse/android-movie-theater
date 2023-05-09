package woowacourse.movie.common.error

sealed interface ViewError {
    val message: String

    class MissingExtras(override val message: String) : ViewError
}
