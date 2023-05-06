package woowacourse.movie.presentation.complete

import woowacourse.movie.domain.model.tools.Movie

interface CompleteContract {
    interface View {
        val presenter: Presenter
    }

    interface Presenter {
        val view: View

        fun getMovieById(movieId: Long): Movie
    }
}
