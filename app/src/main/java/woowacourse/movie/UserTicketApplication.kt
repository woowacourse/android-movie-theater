package woowacourse.movie

import android.app.Application
import woowacourse.movie.model.data.MovieSharedPreferenceImpl
import woowacourse.movie.model.db.UserTicketDatabase
import woowacourse.movie.model.notification.MovieAlarmManager

class UserTicketApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        UserTicketDatabase.initialize(this)
        MovieSharedPreferenceImpl.initialize(this)
        MovieAlarmManager.initialize(this)
    }
}
