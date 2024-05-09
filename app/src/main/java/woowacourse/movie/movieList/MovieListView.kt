package woowacourse.movie.movieList

import woowacourse.movie.model.theater.Theater
import woowacourse.movie.model.ui.Movieitem

interface MovieListView {
    fun showToast(message: String)

    fun updateAdapter(displayData: List<Movieitem>)

    fun navigateToCinemaView(theater: Theater)
}
