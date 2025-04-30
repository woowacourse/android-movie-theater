package woowacourse.movie.view.movie

import woowacourse.movie.model.Movie

interface TheaterClickListener {
    fun onTheaterClick(
        movie: Movie,
        theaterName: String,
    )
}
