package woowacourse.movie.ui.home.presenter

import woowacourse.movie.model.MovieListModel
import woowacourse.movie.ui.common.BaseView

interface HomeContract {
    interface View : BaseView<Presenter> {
        var movieWithAdvertisement: List<MovieListModel>
    }

    interface Presenter {

        fun getMovieWithAdvertisement()
    }
}
