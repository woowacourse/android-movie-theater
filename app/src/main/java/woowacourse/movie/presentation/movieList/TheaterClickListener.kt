package woowacourse.movie.presentation.movieList

interface TheaterClickListener {
    fun onTheaterClick(
        theaterId: Long,
        movieId: Long,
    )
}
