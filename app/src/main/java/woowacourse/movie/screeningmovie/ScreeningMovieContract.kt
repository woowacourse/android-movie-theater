package woowacourse.movie.screeningmovie

interface ScreeningMovieContract {
    interface View {
        fun showMovies(movies: List<ScreeningItem>)

        fun showTheaters(screeningMovieId: Long)
    }

    interface Presenter {
        fun startReservation(screeningMovieId: Long)

        fun loadScreeningMovies()
    }
}
