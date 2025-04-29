package woowacourse.movie.presenter.movies

import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.theater.MovieScreeningInfoByTheaters

interface MoviesContracts {
    interface View {
        fun showMovies(movies: List<Movie>)

        fun showTheaters(movieScreeningInfoByTheaters: MovieScreeningInfoByTheaters)

        fun showAdvertisement(url: String)
    }

    interface Presenter {
        fun initView()

        fun onTheaterRequested(movieId: Long)

        fun onAdvertisementRequested(url: String)
    }
}
