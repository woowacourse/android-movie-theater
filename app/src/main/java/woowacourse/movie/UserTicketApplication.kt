package woowacourse.movie

import android.app.Application
import woowacourse.movie.model.db.UserTicketDatabase

class UserTicketApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        UserTicketDatabase.initialize(this)
    }
}
