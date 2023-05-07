package woowacourse.movie.presentation.movielist.selecttheater

interface SelectTheaterContract {
    interface View {
        val presenter: Presenter

        fun setViewByScreeningState(screeningState: Boolean)
    }

    interface Presenter {
        val view: View

        fun getTheatersByMovieId(movieId: Long): List<String>

        fun getTheaterTimeTableCountByMovieId(movieId: Long, theater: String): Int

        fun checkScreeningState()
    }
}
