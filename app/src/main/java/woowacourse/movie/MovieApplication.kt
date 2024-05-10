package woowacourse.movie

import android.app.Application
import woowacourse.movie.data.AppDatabase
import woowacourse.movie.data.reservationref.ReservationRefRepositoryImpl
import woowacourse.movie.data.screeningref.ScreeningRefRepositoryImpl

class MovieApplication : Application() {
    val db by lazy { AppDatabase.getDatabase(this) }
    val reservationRefRepository by lazy { ReservationRefRepositoryImpl(db.reservationDao()) }
    val screeningRefRepository by lazy { ScreeningRefRepositoryImpl(db.screeningDao()) }
}
