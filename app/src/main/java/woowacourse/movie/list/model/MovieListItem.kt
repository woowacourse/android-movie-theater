package woowacourse.movie.list.model

class MovieListItem(private val position: Int) {
    val type: MovieListItemType
        get() {
            return if (isTurnOfMovie(position)) {
                MovieListItemType.MOVIE
            } else {
                MovieListItemType.ADVERTISEMENT
            }
        }

    private fun isTurnOfMovie(position: Int): Boolean {
        return (position + 1) % 4 != 0
    }
}
