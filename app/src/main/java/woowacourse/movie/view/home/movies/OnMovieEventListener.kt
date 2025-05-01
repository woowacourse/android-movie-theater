package woowacourse.movie.view.home.movies

import woowacourse.movie.domain.Movie

interface OnMovieEventListener {
    fun onClickShowTheater(movie: Movie)
}
