package woowacourse.movie.view.movieList

import woowacourse.movie.entity.Ads
import woowacourse.movie.entity.Movies
import woowacourse.movie.repository.AdRepository
import woowacourse.movie.repository.MovieRepository

class MovieListPresenter(
    private val view: MovieListContract.View,
    private val movieRepository: MovieRepository = Movies(),
    private val adRepository: AdRepository = Ads()
) : MovieListContract.Presenter {
    override fun loadMovieList() {
        view.setMovieList(movieRepository.getAll(), adRepository.getAll())
    }
}
