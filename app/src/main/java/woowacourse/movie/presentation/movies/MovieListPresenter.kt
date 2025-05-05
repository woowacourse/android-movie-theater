package woowacourse.movie.presentation.movies

import woowacourse.movie.data.repository.MovieRepository
import woowacourse.movie.domain.model.Movie
import woowacourse.movie.presentation.movies.adapter.MovieListItem

class MovieListPresenter(
    private val view: MovieListContract.View,
    private val movieRepository: MovieRepository,
) : MovieListContract.Presenter {
    override fun loadMovieList() {
        val movies = movieRepository.fetch()
        view.showMovieList(insertAdvertisement(movies))
    }

    override fun selectMovie(movie: Movie) {
        view.showTheaterList(movie)
    }

    private fun insertAdvertisement(movies: List<Movie>): List<MovieListItem> {
        val result = mutableListOf<MovieListItem>()
        movies.forEachIndexed { index, movie ->
            result.add(MovieListItem.MovieItem(movie))
            if ((index + INDEX_INTERVAL) % ADS_INTERVAL == 0) {
                result.add(MovieListItem.AdsItem())
            }
        }
        return result
    }

    companion object {
        private const val INDEX_INTERVAL = 1
        private const val ADS_INTERVAL = 3
    }
}
