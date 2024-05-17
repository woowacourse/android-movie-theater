package woowacourse.movie.movieList

import woowacourse.movie.model.theater.Theater
import woowacourse.movie.model.ui.MovieItem

interface MovieListView {
    fun showToast(message: String)

    fun updateAdapter(displayData: List<MovieItem>)

    fun navigateToCinemaView(theater: Theater)
}
