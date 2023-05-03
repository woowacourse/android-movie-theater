package woowacourse.movie.ui.main.movieList

import woowacourse.movie.data.AdvRepository
import woowacourse.movie.data.MovieRepository
import woowacourse.movie.model.AdvState
import woowacourse.movie.model.MovieState

class MovieListPresenter(
    private val movieRepository: MovieRepository = MovieRepository,
    private val advRepository: AdvRepository = AdvRepository
) : MovieListContract.Presenter {
    override fun getMovieList(): List<MovieState> {
        return movieRepository.allMovies()
    }

    override fun getAdvList(): List<AdvState> {
        return advRepository.allAdv()
    }
}
