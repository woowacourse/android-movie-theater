package woowacourse.movie

import android.app.Application
import woowacourse.movie.provider.DatabaseProvider
import woowacourse.movie.provider.RepositoryProvider
import woowacourse.movie.provider.ReservationListProvider
import woowacourse.movie.provider.SeatSelectionProvider
import woowacourse.movie.provider.SettingProvider

class MovieTheaterApplication : Application() {
    val databaseProvider: DatabaseProvider by lazy { DatabaseProvider(this) }
    val repositoryProvider: RepositoryProvider by lazy { RepositoryProvider(this) }
    val reservationListProvider: ReservationListProvider by lazy { ReservationListProvider(this) }
    val seatSelectionProvider: SeatSelectionProvider by lazy { SeatSelectionProvider(this) }
    val settingProvider: SettingProvider by lazy { SettingProvider(this) }
}
