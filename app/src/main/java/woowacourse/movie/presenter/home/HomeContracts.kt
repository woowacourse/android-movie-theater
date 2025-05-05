package woowacourse.movie.presenter.home

import woowacourse.movie.model.theater.TheaterMovieSchedules
import woowacourse.movie.view.home.MovieType

interface HomeContracts {
    interface View {
        fun showMovies(movies: List<MovieType>)

        fun showTheaters(theaterMovieSchedules: TheaterMovieSchedules)

        fun showAdvertisement(url: String)
    }

    interface Presenter {
        fun updateView()

        fun updateTheater(movieId: Long)

        fun updateAdvertisement(url: String)
    }
}
