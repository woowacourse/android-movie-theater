package woowacourse.movie

import android.app.Application
import woowacourse.movie.data.TicketInfoDatabase

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        TicketInfoDatabase.getDatabase(this)
    }
}
