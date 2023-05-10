package woowacourse.movie.view.main

interface MainContract {
    interface View {
        fun setScreen(screen: Screen)
    }

    interface Presenter {
        fun changeScreen(screen: Screen)
    }
}
