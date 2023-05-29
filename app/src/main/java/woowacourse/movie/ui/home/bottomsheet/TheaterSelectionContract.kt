package woowacourse.movie.ui.home.bottomsheet

import woowacourse.movie.uimodel.TheaterModel

interface TheaterSelectionContract {
    interface View {
        val presenter: Presenter

        fun setTheaterList(theaters: List<TheaterModel>)
    }

    interface Presenter {
        fun setTheaterList()
    }
}
