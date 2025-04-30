package woowacourse.movie.presentation.movies

import woowacourse.movie.R
import woowacourse.movie.data.MovieData
import woowacourse.movie.domain.model.movie.Movie

class MoviesPresenter(
    private val view: MoviesContract.View,
    private val movieData: MovieData,
) : MoviesContract.Presenter {
    override fun onViewCreated() {
        val screeningMovies = movieData.getData()
        view.showMovies(insertAdvertisement(screeningMovies))
    }

    override fun onMovieClicked(movie: Movie) {
        view.showTheaterSelectDialog(movie)
    }

    private fun insertAdvertisement(movies: List<Movie>): List<MoviesItem> {
        val result = mutableListOf<MoviesItem>()
        movies.forEachIndexed { index, movie ->
            result.add(MoviesItem.MovieItem(movie))
            if ((index + INDEX_INTERVAL) % ADS_INTERVAL == 0) {
                result.add(MoviesItem.AdvertisementItem(R.drawable.advertisement))
            }
        }
        return result
    }

    companion object {
        private const val INDEX_INTERVAL = 1
        private const val ADS_INTERVAL = 3
    }
}
