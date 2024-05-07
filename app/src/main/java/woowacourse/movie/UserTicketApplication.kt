package woowacourse.movie

import android.app.Application
import woowacourse.movie.model.db.UserTicketRepository

class UserTicketApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        UserTicketRepository.initialize(this)
    }
}
