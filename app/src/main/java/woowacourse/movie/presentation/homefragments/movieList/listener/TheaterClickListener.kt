package woowacourse.movie.presentation.homefragments.movieList.listener

interface TheaterClickListener {
    fun onTheaterClick(
        theaterId: Long,
        movieId: Long,
    )
}
