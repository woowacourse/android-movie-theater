package woowacourse.movie.ui.home.presenter

import woowacourse.movie.model.MovieListModel.AdModel
import woowacourse.movie.model.MovieListModel.MovieModel
import woowacourse.movie.ui.common.BaseView
import woowacourse.movie.ui.home.adapter.HomeAdapter

interface HomeContract {
    interface View : BaseView<Presenter> {
        var homeAdapter: HomeAdapter
        fun moveToDetailActivity(movie: MovieModel)
        fun moveToWebPage(ad: AdModel)
    }

    interface Presenter {

        fun initAdapter()
    }
}
