package woowacourse.movie.ui.main.movieList

import com.example.domain.repository.AdvRepository
import com.example.domain.repository.MovieRepository
import woowacourse.movie.model.mapper.asPresentation

class MovieListPresenter(
    private val view: MovieListContract.View,
    private val movieRepository: MovieRepository = MovieRepository,
    private val advRepository: AdvRepository = AdvRepository
) : MovieListContract.Presenter {
    override fun getAdapter() {
        view.setAdapter(
            movieRepository.allMovies().map { it.asPresentation() },
            advRepository.allAdv().map { it.asPresentation() }
        )
    }
}
