package woowacourse.movie.home.presenter.contract

import woowacourse.movie.home.view.adapter.movie.HomeContent

interface MovieHomeContract {
    interface View {
        fun displayHomeContents(homeContents: List<HomeContent>)
    }

    interface Presenter {
        fun loadHomeContents()
    }
}
