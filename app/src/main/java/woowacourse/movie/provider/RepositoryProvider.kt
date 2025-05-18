package woowacourse.movie.provider

import android.content.Context
import woowacourse.movie.MovieTheaterApplication
import woowacourse.movie.repository.SettingRepository
import woowacourse.movie.repository.TicketRepository

class RepositoryProvider(context: Context) {
    private val application = context.applicationContext as MovieTheaterApplication

    val ticketRepository: TicketRepository =
        TicketRepository(
            application.databaseProvider.ticketDao,
        )

    val settingRepository: SettingRepository =
        SettingRepository(
            application.getSharedPreferences("setting", Context.MODE_PRIVATE),
        )
}
