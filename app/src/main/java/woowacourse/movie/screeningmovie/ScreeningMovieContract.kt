package woowacourse.movie.screeningmovie

interface ScreeningMovieContract {
    interface View {
        fun showMovies(movies: List<ScreeningItem>)
    }

    interface Presenter {
        fun loadScreeningMovies()
    }
}
