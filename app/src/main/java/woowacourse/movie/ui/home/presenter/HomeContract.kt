package woowacourse.movie.ui.home.presenter

import woowacourse.movie.ui.common.BaseView
import woowacourse.movie.ui.home.adapter.HomeAdapter

interface HomeContract {
    interface View : BaseView<Presenter> {
        var homeAdapter: HomeAdapter
    }

    interface Presenter {

        fun initAdapter()
    }
}
