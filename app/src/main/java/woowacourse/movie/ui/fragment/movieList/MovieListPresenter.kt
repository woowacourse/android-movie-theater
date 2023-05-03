package woowacourse.movie.ui.fragment.movieList

import woowacourse.movie.data.AdvRepository
import woowacourse.movie.data.MovieRepository
import woowacourse.movie.model.AdvState
import woowacourse.movie.model.MovieState

class MovieListPresenter(
    private val movieRepository: MovieRepository = MovieRepository,
    private val advRepository: AdvRepository = AdvRepository
) {
    fun getMovieList(): List<MovieState> {
        return movieRepository.allMovies()
    }

    fun getAdvList(): List<AdvState> {
        return advRepository.allAdv()
    }
}
