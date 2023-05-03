package woowacourse.movie.ui.seat

import woowacourse.movie.model.BookedMovie
import woowacourse.movie.movie.Movie
import woowacourse.movie.movie.MovieRepository

class SeatPresenter(
    private val view: SeatContract.View,
    bookedMovie: BookedMovie,
) : SeatContract.Presenter {

    override val movie: Movie = MovieRepository.getMovie(bookedMovie.movieId)

    override fun initMovieTitle() {
        view.initMovieTitleText(movie.title)
    }
}
