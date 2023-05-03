package woowacourse.movie.feature.movieList

import woowacourse.movie.model.AdvState
import woowacourse.movie.model.MovieState

class MoviesPresenter(
    val view: MovieListContract.View
) : MovieListContract.Presenter {
    override fun clickMovieItem(movie: MovieState) {
        view.navigateMovieDetail(movie)
    }

    override fun clickAdvItem(adv: AdvState) {
        view.navigateAdbDetail(adv)
    }
}
