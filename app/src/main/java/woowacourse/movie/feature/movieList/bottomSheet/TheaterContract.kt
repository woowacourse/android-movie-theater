package woowacourse.movie.feature.movieList.bottomSheet

import woowacourse.movie.model.MovieState
import woowacourse.movie.model.SelectTheaterAndMovieState
import woowacourse.movie.model.TheaterScreeningState

interface TheaterContract {
    interface View {
        fun setTheaterAdapter(theaters: List<TheaterItemModel>)
        fun selectTheater(theater: SelectTheaterAndMovieState)
    }

    interface Presenter {
        fun loadTheatersData(movie: MovieState)
        fun clickTheater(theater: TheaterScreeningState, movie: MovieState)
    }
}
