package woowacourse.movie.view.moviemain.movielist

import woowacourse.movie.data.movie.MoviePosterRepository
import woowacourse.movie.domain.repository.MovieRepository
import woowacourse.movie.view.mapper.toUiModel
import woowacourse.movie.view.model.MovieUiModel

class MovieListPresenter(private val movieRepository: MovieRepository, private val moviePosterRepository: MoviePosterRepository) : MovieListContract.Presenter {
    override fun getMovieListData(): List<MovieUiModel> {
        return movieRepository.findAll()
            .map { it.toUiModel(moviePosterRepository.findPoster(it.title)) }
    }
}
