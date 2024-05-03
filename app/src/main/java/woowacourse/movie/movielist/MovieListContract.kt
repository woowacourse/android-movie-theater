package woowacourse.movie.movielist

import woowacourse.movie.movielist.uimodel.ListItemUiModel

interface MovieListContract {
    interface View {
        fun showMovies(movies: List<ListItemUiModel>)

        fun showTheaters(screeningMovieId: Long)
    }

    interface Presenter {
        fun startReservation(screeningMovieId: Long)

        fun loadScreeningMovies()
    }
}
