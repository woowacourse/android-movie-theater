package woowacourse.movie.di

import android.content.Context
import woowacourse.movie.data.BookingHistoryDetailsDatabase
import woowacourse.movie.data.repository.BookingHistoryRepositoryImpl
import woowacourse.movie.domain.repository.BookingHistoryRepository

object RepositoryInjector {
    fun provideBookingHistoryRepository(context: Context): BookingHistoryRepository {
        val dao = BookingHistoryDetailsDatabase.getDatabase(context).bookingHistoryDetailsDao()
        return BookingHistoryRepositoryImpl(dao)
    }
}
