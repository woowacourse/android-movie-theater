package woowacourse.movie.movie

import woowacourse.movie.DefaultMovieData.mockTheaterList
import woowacourse.movie.mapper.toDomain
import woowacourse.movie.mapper.toUiModel
import woowacourse.movie.model.Movie
import woowacourse.movie.ui.model.MovieUiModel

class MoviePresenter(
    private val view: MovieContract.View,
) : MovieContract.Presenter {
    val theater = mockTheaterList()

    override fun initializeData() {
        val movies = getReservableMovies().map { it.toUiModel() }
        view.setupMovieList(movies)
    }

    private fun getReservableMovies(): List<Movie> {
        return theater.map { it.schedules }.flatten().map { it.movie }.distinct()
    }

    override fun setTheaters(movie: MovieUiModel) {
        val domainMovie = movie.toDomain()

        val domainTheaters = theater.filter { it.schedules.map { it.movie }.contains(domainMovie) }

        view.showTheaterDialog(ArrayList(domainTheaters.map { it.toUiModel(domainMovie) }), movie)
    }
}
