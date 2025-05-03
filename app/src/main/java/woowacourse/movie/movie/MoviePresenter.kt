package woowacourse.movie.movie

import woowacourse.movie.DefaultMovieData.mockTheaterList
import woowacourse.movie.R
import woowacourse.movie.mapper.toDomain
import woowacourse.movie.mapper.toUiModel
import woowacourse.movie.model.Movie
import woowacourse.movie.ui.model.MovieUiModel

class MoviePresenter(
    private val view: MovieContract.View,
) : MovieContract.Presenter {
    val theater = mockTheaterList()

    override fun initializeData() {
        val movies = getReservableMovies().map { MovieListItem.MovieItem(it.toUiModel()) }

        val result = mutableListOf<MovieListItem>()

        movies.forEachIndexed { index, movieItem ->
            result.add(movieItem)
            if ((index + INDEX_OFFSET) % AD_INSERT_INTERVAL == 0) {
                result.add(MovieListItem.AdvertisementItem(R.drawable.img_advertisement))
            }
        }

        view.setupMovieList(result)
    }

    private fun getReservableMovies(): List<Movie> {
        return theater.map { it.schedules }.flatten().map { it.movie }.distinct()
    }

    override fun setTheaters(movie: MovieUiModel) {
        val domainMovie = movie.toDomain()

        val domainTheaters = theater.filter { it.schedules.map { it.movie }.contains(domainMovie) }

        view.showTheaterDialog(ArrayList(domainTheaters.map { it.toUiModel(domainMovie) }), movie)
    }

    companion object {
        private const val AD_INSERT_INTERVAL: Int = 3
        private const val INDEX_OFFSET: Int = 1
    }
}
