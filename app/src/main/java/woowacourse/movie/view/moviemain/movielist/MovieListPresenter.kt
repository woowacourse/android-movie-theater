package woowacourse.movie.view.moviemain.movielist

import woowacourse.movie.domain.repository.MovieRepository
import woowacourse.movie.view.mapper.toUiModel

class MovieListPresenter(
    private val view: MovieListContract.View,
    private val movieRepository: MovieRepository,
) : MovieListContract.Presenter {
    override fun fetchMovieList() {
        val movies = movieRepository.findAll()
            .map { it.toUiModel() }
        view.showMovieList(movies)
    }
}
