package woowacourse.movie

import android.app.Application
import androidx.room.Room
import woowacourse.movie.data.database.BookingDatabase
import woowacourse.movie.data.database.BookingDatabase.Companion.DATABASE_NAME
import woowacourse.movie.data.repository.BookingRepositoryImpl

class MovieApplication : Application() {
    val bookingDatabase by lazy {
        Room
            .databaseBuilder(this, BookingDatabase::class.java, DATABASE_NAME)
            .allowMainThreadQueries()
            .build()
    }
    val bookingRepository by lazy { BookingRepositoryImpl(bookingDatabase.bookingDao()) }
}
