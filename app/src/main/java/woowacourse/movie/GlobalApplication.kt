package woowacourse.movie

import android.app.Application
import woowacourse.movie.data.BookedTicketDatabase
import woowacourse.movie.data.BookedTicketRepositoryImpl
import woowacourse.movie.providers.BookedTicketRepositoryProvider

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initBookedTicketRepositoryProvider()
    }

    private fun initBookedTicketRepositoryProvider() {
        BookedTicketRepositoryProvider.init(
            BookedTicketRepositoryImpl(
                database = BookedTicketDatabase.getInstance(this)
            )
        )
    }
}
