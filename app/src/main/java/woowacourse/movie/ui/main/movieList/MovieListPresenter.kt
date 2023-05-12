package woowacourse.movie.ui.main.movieList

import com.example.domain.repository.AdvRepository
import com.example.domain.repository.MovieRepository
import com.example.domain.repositoryImpl.AdvSameRepository
import com.example.domain.repositoryImpl.MovieSameRepository
import woowacourse.movie.model.mapper.asPresentation

class MovieListPresenter(
    private val view: MovieListContract.View,
    private val movieRepository: MovieRepository = MovieSameRepository,
    private val advRepository: AdvRepository = AdvSameRepository
) : MovieListContract.Presenter {
    init {
        setUpMovieList()
    }
    override fun setUpMovieList() {
        view.showMovieList(
            movieRepository.allMovies().map { it.asPresentation() },
            advRepository.allAdv().map { it.asPresentation() }
        )
    }
}
