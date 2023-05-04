package woowacourse.movie.ui.home

import woowacourse.movie.model.main.MainData

interface HomeContract {

    interface View {

        fun initAdapter(mainData: List<MainData>)
    }

    interface Presenter {

        fun initMainData()
    }
}
