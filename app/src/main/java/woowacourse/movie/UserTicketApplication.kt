package woowacourse.movie

import android.app.Application
import woowacourse.movie.model.db.UserTicketDatabase
import woowacourse.movie.model.db.UserTicketRepositoryImpl

class UserTicketApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        UserTicketRepositoryImpl.initializeRepository(
            UserTicketDatabase.database(this).userTicketDao(),
        )
    }
}
