package woowacourse.movie.movie.history

import woowacourse.movie.movie.dto.movie.BookingMovieEntity

interface HistoryContract {
    interface View {
        val presenter: Presenter

        fun setUpHistoryData(history: List<BookingMovieEntity>)
    }

    interface Presenter {
        fun initFragment()
        fun getHistory(): List<BookingMovieEntity>
    }
}
