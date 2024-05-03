package woowacourse.movie.screeningmovie

import woowacourse.movie.screeningmovie.uimodel.ListItemUiModel

interface ScreeningMovieContract {
    interface View {
        fun showMovies(movies: List<ListItemUiModel>)

        fun showTheaters(screeningMovieId: Long)
    }

    interface Presenter {
        fun startReservation(screeningMovieId: Long)

        fun loadScreeningMovies()
    }
}
