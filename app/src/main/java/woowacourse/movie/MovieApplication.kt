package woowacourse.movie

import android.app.Application
import woowacourse.movie.data.MovieSharedPreferences
import woowacourse.movie.data.database.MovieDatabase
import woowacourse.movie.data.repository.LocalTicketRepository
import woowacourse.movie.data.repository.NotificationSettingRepository
import woowacourse.movie.presentation.notification.ticket.TicketAlarm
import java.util.TimeZone

class MovieApplication : Application() {
    private val database by lazy { MovieDatabase.getDatabase(this) }
    val ticketRepository by lazy { LocalTicketRepository(database.ticketDao) }
    val settingRepository by lazy {
        NotificationSettingRepository(
            MovieSharedPreferences.getSettingsSharedPreferences(this),
        )
    }

    val ticketAlarm by lazy { TicketAlarm(applicationContext) }

    override fun onCreate() {
        super.onCreate()
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"))
    }
}
