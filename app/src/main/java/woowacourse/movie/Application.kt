package woowacourse.movie

import android.app.Application
import woowacourse.movie.data.TicketInfoDatabase

class Application : Application() {
    lateinit var database: TicketInfoDatabase
        private set

    override fun onCreate() {
        super.onCreate()
        database = TicketInfoDatabase.getDatabase(this)
    }
}
