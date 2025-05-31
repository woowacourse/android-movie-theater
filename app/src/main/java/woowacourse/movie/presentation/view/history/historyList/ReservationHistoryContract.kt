package woowacourse.movie.presentation.view.history.historyList

interface ReservationHistoryContract {
    interface Presenter {
        fun fetchData()
    }

    interface View {
        fun showScreen()
    }
}
