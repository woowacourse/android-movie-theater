package woowacourse.movie.presentation.complete

interface CompleteContract {
    interface View {
        val presenter: Presenter
        fun setMovieTitle(movieTitle: String)
    }

    interface Presenter {
        fun setMovieTitle(movieId: Long)
    }
}
