package woowacourse.movie.ui.cinema.presenter

import woowacourse.movie.ui.common.BaseView

interface CinemaContract {
    interface View : BaseView<Presenter>

    interface Presenter
}
