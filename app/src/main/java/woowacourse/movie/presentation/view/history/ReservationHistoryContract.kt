package woowacourse.movie.presentation.view.history

interface ReservationHistoryContract {
    interface Presenter {
        fun fetchData()
    }

    interface View {
        fun showScreen()
    }
}
