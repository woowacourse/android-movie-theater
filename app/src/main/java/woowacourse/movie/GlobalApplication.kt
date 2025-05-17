package woowacourse.movie

import android.app.Application
import woowacourse.movie.data.BookedTicketDatabase
import woowacourse.movie.data.BookedTicketRepositoryImpl
import woowacourse.movie.providers.BookedTicketRepositoryProvider
import woowacourse.movie.providers.StorageProvider
import woowacourse.movie.providers.StorageProvider.PREFERENCE_KEY

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initBookedTicketRepositoryProvider()
        initSharedPreferences()
    }

    private fun initBookedTicketRepositoryProvider() {
        BookedTicketRepositoryProvider.init(
            BookedTicketRepositoryImpl(
                database = BookedTicketDatabase.getInstance(this),
            ),
        )
    }

    private fun initSharedPreferences() {
        StorageProvider.init(this.getSharedPreferences(PREFERENCE_KEY, MODE_PRIVATE))
    }
}
