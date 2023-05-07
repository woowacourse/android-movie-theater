package woowacourse.movie.data.model.uimodel

enum class MovieAdapterViewType(val value: Int) {
    MOVIE(1),
    ADVERTISEMENT(2);

    companion object {
        private const val VIEW_TYPE_ERROR = "잘못된 viewtype이 들어왔어요"

        fun find(number: Int): MovieAdapterViewType {
            return values().find { it.value == number } ?: throw IllegalArgumentException(
                VIEW_TYPE_ERROR
            )
        }
    }
}
