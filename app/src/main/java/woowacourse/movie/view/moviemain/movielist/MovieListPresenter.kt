package woowacourse.movie.view.moviemain.movielist

import woowacourse.movie.R
import woowacourse.movie.data.MovieMockRepository
import woowacourse.movie.domain.movie.Movie
import woowacourse.movie.domain.repository.MovieRepository
import woowacourse.movie.view.mapper.toUiModel
import woowacourse.movie.view.model.MovieListModel

class MovieListPresenter : MovieListContract.Presenter {
    override fun getMovieListData(): List<MovieListModel> {
        val movieRepository: MovieRepository = MovieMockRepository
        val movies = movieRepository.findAll()
        return generateMovieListData(movies)
    }

    private fun generateMovieListData(movies: List<Movie>): List<MovieListModel> {
        val ad = MovieListModel.MovieAdModel(
            R.drawable.woowacourse_banner,
            "https://woowacourse.github.io/",
        )

        return mixMovieAdData(movies, ad, AD_POST_INTERVAL)
    }

    private fun mixMovieAdData(
        movies: List<Movie>,
        ad: MovieListModel.MovieAdModel,
        adPostInterval: Int,
    ): List<MovieListModel> {
        val dataList = mutableListOf<MovieListModel>()
        movies.forEachIndexed { index, movie ->
            if (index % adPostInterval == adPostInterval - 1) {
                dataList.add(movie.toUiModel())
                dataList.add(ad)
                return@forEachIndexed
            }
            dataList.add(movie.toUiModel())
        }
        return dataList
    }

    companion object {
        private const val AD_POST_INTERVAL = 3
    }
}
