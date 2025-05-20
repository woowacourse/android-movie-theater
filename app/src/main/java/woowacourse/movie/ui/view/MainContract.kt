package woowacourse.movie.ui.view

interface MainContract {
    interface Presenter {
        fun presentScreen(mainScreen: MainScreen)
    }

    interface View {
        fun updateScreen(mainScreen: MainScreen)
    }
}
