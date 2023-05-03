package woowacourse.movie.ui.main.movieList

import woowacourse.movie.model.AdvState
import woowacourse.movie.model.MovieState

interface MovieListContract {
    interface View

    interface Presenter {
        fun getMovieList(): List<MovieState>
        fun getAdvList(): List<AdvState>
    }
}
