package woowacourse.movie.presenter.home

import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.theater.TheaterMovieSchedules

interface HomeContracts {
    interface View {
        fun showMovies(movies: List<Movie>)

        fun showTheaters(theaterMovieSchedules: TheaterMovieSchedules)

        fun showAdvertisement(url: String)
    }

    interface Presenter {
        fun initView()

        fun onTheaterRequested(movieId: Long)

        fun onAdvertisementRequested(url: String)
    }
}
