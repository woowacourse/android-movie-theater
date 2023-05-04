package woowacourse.movie.view.moviemain.movielist

import woowacourse.movie.R
import woowacourse.movie.data.MovieMockRepository
import woowacourse.movie.domain.Movie
import woowacourse.movie.domain.repository.MovieRepository
import woowacourse.movie.view.mapper.toUiModel
import woowacourse.movie.view.model.MovieListModel

class MovieListPresenter(
    private val view: MovieListContract.View
) : MovieListContract.Presenter {

    private val movieRepository: MovieRepository = MovieMockRepository

    override fun loadMovieList() {
        val movies = movieRepository.findAll()
        val dataList = generateMovieListData(movies)
        view.showMovieList(dataList)
    }

    private fun generateMovieListData(movies: List<Movie>): List<MovieListModel> {
        val ad = MovieListModel.MovieAdModel(
            R.drawable.woowacourse_banner,
            "https://woowacourse.github.io/"
        )

        return mixMovieAdData(movies, ad, AD_POST_INTERVAL)
    }

    private fun mixMovieAdData(
        movies: List<Movie>,
        ad: MovieListModel.MovieAdModel,
        adPostInterval: Int
    ): List<MovieListModel> {
        return movies.flatMapIndexed { index, movie ->
            if (index % adPostInterval == adPostInterval - 1) {
                listOf(movie.toUiModel(), ad)
            } else {
                listOf(movie.toUiModel())
            }
        }
    }

    override fun onItemClick(item: MovieListModel) {
        when (item) {
            is MovieListModel.MovieUiModel -> {
                view.openReservationActivity(item)
            }
            is MovieListModel.MovieAdModel -> {
                view.openAdPage(item)
            }
        }
    }

    companion object {
        private const val AD_POST_INTERVAL = 3
    }
}
