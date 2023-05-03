package woowacourse.movie.feature.movieList

import woowacourse.movie.model.AdvState
import woowacourse.movie.model.MovieState

interface MovieListContract {
    interface View {
        fun navigateMovieDetail(movie: MovieState)
        fun navigateAdbDetail(adv: AdvState)
    }

    interface Presenter {
        fun clickMovieItem(movie: MovieState)
        fun clickAdvItem(adv: AdvState)
    }
}
