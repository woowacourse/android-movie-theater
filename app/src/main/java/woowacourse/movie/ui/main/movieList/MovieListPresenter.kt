package woowacourse.movie.ui.main.movieList

import woowacourse.movie.data.AdvRepository
import woowacourse.movie.data.MovieRepository

class MovieListPresenter(
    private val view: MovieListContract.View,
    private val movieRepository: MovieRepository = MovieRepository,
    private val advRepository: AdvRepository = AdvRepository
) : MovieListContract.Presenter {
    override fun getAdapter() {
        view.setAdapter(movieRepository.allMovies(), advRepository.allAdv())
    }
}
