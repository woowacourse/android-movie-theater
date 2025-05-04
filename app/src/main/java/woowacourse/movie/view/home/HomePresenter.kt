package woowacourse.movie.view.home

import woowacourse.movie.domain.Movie
import woowacourse.movie.domain.dummy_movies

class HomePresenter(
    private val view: HomeContract.View,
) : HomeContract.Presenter {
    override fun fetchData() {
        val movies: List<Movie> = dummy_movies
        view.showMoviesScreen(movies) { movie ->
            view.showTheaterSelectDialog(movie) { showings ->
                view.navigateToReservation(movie, showings)
            }
        }
    }
}
