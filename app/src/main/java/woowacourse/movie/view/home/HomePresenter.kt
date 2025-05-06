package woowacourse.movie.view.home

import woowacourse.movie.domain.Movie
import woowacourse.movie.view.home.movies.toMovieUi

class HomePresenter(
    private val view: HomeContract.View,
) : HomeContract.Presenter {
    override fun fetchData() {
        val movies: List<Movie> = Movie.dummy
        view.showMoviesScreen(movies.map { it.toMovieUi() })
    }
}
