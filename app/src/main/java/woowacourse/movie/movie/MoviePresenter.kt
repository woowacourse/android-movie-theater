package woowacourse.movie.movie

import woowacourse.movie.DefaultMovieData.mockTheaterList
import woowacourse.movie.mapper.toDomain
import woowacourse.movie.mapper.toUiModel
import woowacourse.movie.model.AdInserter
import woowacourse.movie.model.Movie
import woowacourse.movie.ui.model.MovieUiModel

class MoviePresenter(
    private val view: MovieContract.View,
) : MovieContract.Presenter {
    val theater = mockTheaterList()

    override fun initializeData() {
        val movies = getReservableMovies()
        val movieFeedItem = AdInserter.insertAd(movies)

        view.setupMovieList(movieFeedItem.map { it.toUiModel() })
    }

    private fun getReservableMovies(): List<Movie> {
        return theater.map { it.screeningInfos }.flatten().map { it.movie }.distinct()
    }

    override fun setTheaters(movie: MovieUiModel) {
        val domainMovie = movie.toDomain()

        val domainTheaters = theater.filter { it.screeningInfos.map { it.movie }.contains(domainMovie) }

        view.showTheaterDialog(ArrayList(domainTheaters.map { it.toUiModel(domainMovie) }), movie)
    }
}
