package woowacourse.movie.ui.movielist.presenter

import woowacourse.movie.data.dummy.DUMMY_ADS
import woowacourse.movie.data.mapper.MovieMapper
import woowacourse.movie.data.repository.MovieRepository
import woowacourse.movie.domain.model.item.MovieListItem.AdItem
import woowacourse.movie.domain.model.item.MovieListItem.Companion.movieListItems
import woowacourse.movie.domain.model.item.MovieListItem.MovieItem
import woowacourse.movie.ui.movielist.contract.MovieListContract
import kotlin.concurrent.thread

class MovieListPresenter(
    private val movieListView: MovieListContract.View,
    private val movieRepository: MovieRepository,
) : MovieListContract.Presenter {
    private lateinit var movies: List<MovieItem>

    override fun loadMovieList() {
        fetchMovies()
        val ads: List<AdItem> = fetchAds()
        movieListView.setMoveListItems(movieListItems(movies, ads))
    }

    private fun fetchMovies() {
        thread {
            movies = movieRepository.getAll().map { MovieItem(MovieMapper.toModel(it)) }
        }.join()
    }

    private fun fetchAds(): List<AdItem> = DUMMY_ADS.map { AdItem(it) }
}
