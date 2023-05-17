package woowacourse.movie.fragment.bookhistory

import woowacourse.movie.model.BookingHistoryData

interface BookHistoryContract {

    interface View {
        var presenter: Presenter
        fun setMovieRecyclerView(bookingHistoryData: List<BookingHistoryData>)
    }

    interface Presenter {
        fun setMovieRecyclerView()
    }
}
