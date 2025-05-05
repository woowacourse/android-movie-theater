package woowacourse.movie.movie

import woowacourse.movie.domain.Movie

interface MovieClickListener {
    fun navigateToBook(movie: Movie)
    fun navigateToAd()
}
