package woowacourse.movie.presentation.movieList.listener

interface TheaterClickListener {
    fun onTheaterClick(
        theaterId: Long,
        movieId: Long,
    )
}
