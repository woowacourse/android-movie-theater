package woowacourse.movie.view.moviemain.movielist

import woowacourse.movie.data.MovieMockRepository
import woowacourse.movie.data.MoviePosterMockRepository
import woowacourse.movie.data.MoviePosterRepository
import woowacourse.movie.domain.repository.MovieRepository
import woowacourse.movie.view.mapper.toUiModel
import woowacourse.movie.view.model.MovieUiModel

class MovieListPresenter : MovieListContract.Presenter {
    override fun getMovieListData(): List<MovieUiModel> {
        val movieRepository: MovieRepository = MovieMockRepository
        val moviePosterRepository: MoviePosterRepository = MoviePosterMockRepository
        return movieRepository.findAll()
            .map { it.toUiModel(moviePosterRepository.findPoster(it.title)) }
    }
}
