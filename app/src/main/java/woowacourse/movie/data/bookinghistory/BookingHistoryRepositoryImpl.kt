package woowacourse.movie.data.bookinghistory

import android.content.Context
import woowacourse.movie.domain.model.movie.MovieTicket
import kotlin.concurrent.thread

class BookingHistoryRepositoryImpl private constructor(private val database: MovieDatabase) :
    BookingHistoryRepository {
        override fun saveBooking(ticket: MovieTicket) {
            thread {
                database.bookingHistoryDao().insert(BookingHistoryMapper.mapToBookingHistory(ticket))
            }
        }

        override fun getBookings(onLoaded: (List<MovieTicket>) -> Unit) {
            thread {
                val movieTickets =
                    database.bookingHistoryDao().getAll().map { bookingHistory ->
                        BookingHistoryMapper.mapFromBookingHistory(bookingHistory)
                    }
                onLoaded(movieTickets)
            }
        }

        companion object {
            private var _INSTANCE: BookingHistoryRepositoryImpl? = null
            val INSTANCE: BookingHistoryRepositoryImpl
                get() = _INSTANCE ?: throw IllegalArgumentException("[ERROR] 저장소가 초기화되지 않았습니다.")

            fun initialize(context: Context) {
                if (_INSTANCE == null) {
                    _INSTANCE = BookingHistoryRepositoryImpl(MovieDatabase.getDatabase(context))
                }
            }
        }
    }
