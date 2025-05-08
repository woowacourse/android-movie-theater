package woowacourse.movie.contract

interface MainContract {
    interface Presenter {
        fun presentScreen(screenId: Int)
    }

    interface View {
        fun updateScreen(screenId: Int)
    }
}
