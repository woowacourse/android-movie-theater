package woowacourse.movie.view.moviemain.movielist

import woowacourse.movie.R
import woowacourse.movie.data.MovieMockRepository
import woowacourse.movie.domain.repository.MovieRepository
import woowacourse.movie.view.mapper.toUiModel
import woowacourse.movie.view.model.MovieUiModel

class MovieListPresenter : MovieListContract.Presenter {
    override fun getMovieListData(): List<MovieUiModel> {
        val movieRepository: MovieRepository = MovieMockRepository
        val movies = movieRepository.findAll().map { it.toUiModel(R.drawable.harry_potter1_poster) }
        return movies
    }

    companion object {
        private const val AD_POST_INTERVAL = 3
    }
}
