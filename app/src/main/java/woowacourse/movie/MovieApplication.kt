package woowacourse.movie

import android.app.Application
import woowacourse.movie.data.bookinghistory.BookingHistoryRepositoryImpl
import woowacourse.movie.data.notification.NotificationPreferenceImpl
import java.util.TimeZone

class MovieApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"))
        BookingHistoryRepositoryImpl.initialize(this)
        NotificationPreferenceImpl.initialize(this)
    }
}
