package woowacourse.movie.model.ui

data class MovieItemDisplay(
    val title: String,
    val releaseDate: String,
    val runningTime: String,
) : MovieDisplay(ITEM_VIEW_TYPE_MOVIE) {
    companion object {
        const val ITEM_VIEW_TYPE_MOVIE = 0
    }
}
