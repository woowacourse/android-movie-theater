import woowacourse.movie.model.theater.Theater
import woowacourse.movie.presentation.movieList.model.MovieDisplay

interface MovieListView {
    fun showToast(message: String)

    fun updateAdapter(displayData: List<MovieDisplay>)

    fun navigateToCinemaView(theater: Theater)
}
