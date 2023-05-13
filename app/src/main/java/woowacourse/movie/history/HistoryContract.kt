package woowacourse.movie.history

import woowacourse.movie.dto.movie.BookingMovieDto

interface HistoryContract {
    interface View {
        fun setUpHistoryData(history: List<BookingMovieDto>)
    }

    interface Presenter {
        fun getHistory(): List<BookingMovieDto>
    }
}
