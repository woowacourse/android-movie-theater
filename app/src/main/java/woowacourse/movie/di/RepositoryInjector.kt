package woowacourse.movie.di

import android.content.Context
import woowacourse.movie.data.BookingInfoDatabase
import woowacourse.movie.data.repository.BookingHistoryRepositoryImpl
import woowacourse.movie.domain.repository.BookingHistoryRepository

object RepositoryInjector {
    fun provideBookingHistoryRepository(context: Context): BookingHistoryRepository {
        val dao = BookingInfoDatabase.getDatabase(context).BookingInfoDao()
        return BookingHistoryRepositoryImpl(dao)
    }
}
