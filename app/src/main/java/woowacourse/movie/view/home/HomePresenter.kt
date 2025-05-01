package woowacourse.movie.view.home

import woowacourse.movie.domain.Movie

class HomePresenter(
    private val view: HomeContract.View,
) : HomeContract.Presenter {
    override fun fetchData() {
        val movies: List<Movie> = Movie.dummy
        view.showMoviesScreen(movies) { movie ->
            view.showTheaterSelectDialog(movie) { showings ->
                view.navigateToReservation(movie, showings)
            }
        }
    }
}
