package woowacourse.movie.provider

import android.content.Context
import woowacourse.movie.data.MovieTheaterDatabase
import woowacourse.movie.repository.SettingRepository
import woowacourse.movie.repository.TicketRepository

object RepositoryProvider {
    fun ticketRepository(context: Context): TicketRepository =
        TicketRepository(
            ticketDao(context.applicationContext),
        )

    fun settingRepository(context: Context): SettingRepository =
        SettingRepository(
            context.applicationContext.getSharedPreferences("setting", Context.MODE_PRIVATE),
        )

    fun ticketDao(context: Context) = MovieTheaterDatabase.db(context.applicationContext).ticketDao()
}
