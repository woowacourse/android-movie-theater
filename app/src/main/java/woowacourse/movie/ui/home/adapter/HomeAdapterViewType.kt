package woowacourse.movie.ui.home.adapter

enum class HomeAdapterViewType(val value: Int) {
    MOVIE(0),
    AD(1),
    ;

    companion object {
        fun valueOf(value: Int): HomeAdapterViewType =
            HomeAdapterViewType.values().find { viewHolder ->
                viewHolder.value == value
            } ?: throw IllegalArgumentException()
    }
}
