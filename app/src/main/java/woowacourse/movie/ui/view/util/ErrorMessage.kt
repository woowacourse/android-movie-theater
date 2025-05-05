package woowacourse.movie.ui.view.util

@JvmInline
value class ErrorMessage(
    private val cause: String,
) {
    fun notProvided() = "$cause ${woowacourse.movie.ui.view.util.ErrorMessage.Companion.ERROR_MESSAGE_NO_DATA_FORMAT}"

    fun notSelected() = "$cause ${woowacourse.movie.ui.view.util.ErrorMessage.Companion.ERROR_MESSAGE_NOT_SELECTED_YET_FORMAT}T"

    fun noSuch() = "no such $cause`"

    companion object {
        private const val ERROR_MESSAGE_NO_DATA_FORMAT = "was not provided."
        private const val ERROR_MESSAGE_NOT_SELECTED_YET_FORMAT = "is not selected yet."
    }
}
