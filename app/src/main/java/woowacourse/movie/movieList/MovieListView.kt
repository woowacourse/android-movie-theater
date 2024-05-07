import woowacourse.movie.model.ui.MovieDisplay
import woowacourse.movie.model.theater.Theater

interface MovieListView {
    fun showToast(message: String)

    fun updateAdapter(displayData: List<MovieDisplay>)

    fun navigateToCinemaView(theater: Theater)
}
