package woowacourse.movie.usecase

import woowacourse.movie.model.Movie
import woowacourse.movie.repository.MovieRepository

class FetchAllMoviesUseCase(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(): Result<List<Movie>> {
        return runCatching {
            movieRepository.movies()
        }
    }
}
