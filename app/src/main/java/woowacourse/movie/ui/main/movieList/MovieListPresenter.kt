package woowacourse.movie.ui.main.movieList

import com.example.domain.repository.AdvRepository
import woowacourse.movie.model.mapper.asPresentation
import woowacourse.movie.repository.MovieRepository

class MovieListPresenter(
    private val view: MovieListContract.View,
    private val movieRepository: MovieRepository = MovieRepository,
    private val advRepository: AdvRepository = AdvRepository
) : MovieListContract.Presenter {
    override fun getAdapter() {
        view.setAdapter(
            movieRepository.allMovies(),
            advRepository.allAdv().map { it.asPresentation() }
        )
    }
}
