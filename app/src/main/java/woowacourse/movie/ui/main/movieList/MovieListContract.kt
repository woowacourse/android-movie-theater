package woowacourse.movie.ui.main.movieList

import woowacourse.movie.model.AdvState
import woowacourse.movie.model.MovieState

interface MovieListContract {
    interface View {
        fun setAdapter(movieList: List<MovieState>, advList: List<AdvState>)
    }

    interface Presenter {
        fun getAdapter()
    }
}
