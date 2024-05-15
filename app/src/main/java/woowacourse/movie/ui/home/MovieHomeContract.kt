package woowacourse.movie.ui.home

import woowacourse.movie.domain.MovieContent
import woowacourse.movie.ui.HandleError

interface MovieHomeContract {
    interface View : HandleError {
        fun showMovieContents(movieContents: List<MovieContent>)
    }

    interface Presenter {
        fun loadMovieContents()

        fun handleError(throwable: Throwable)
    }
}
