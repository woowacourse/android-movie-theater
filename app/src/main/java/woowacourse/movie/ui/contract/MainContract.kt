package woowacourse.movie.ui.contract

import woowacourse.movie.ui.view.MainScreen

interface MainContract {
    interface Presenter {
        fun presentScreen(mainScreen: MainScreen)
    }

    interface View {
        fun updateScreen(mainScreen: MainScreen)
    }
}
