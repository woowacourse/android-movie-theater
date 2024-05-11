package woowacourse.movie

import android.app.Application
import android.content.IntentFilter
import androidx.core.content.ContextCompat
import woowacourse.movie.MovieBroadCastReceiver.Companion.RESERVATION_NOTIFICATION_ACTION
import woowacourse.movie.data.MovieRepository
import woowacourse.movie.data.MovieRepositoryStore
import woowacourse.movie.data.datastore.DefaultNotificationDataStore

class MovieReservationApp : Application() {
    val movieRepository: MovieRepository by lazy {
        MovieRepositoryStore.instance(applicationContext).movieRepository()
    }

    val notificationDatastore by lazy {
        DefaultNotificationDataStore.instance(applicationContext)
    }

    private val reservationBroadcastReceiver: MovieBroadCastReceiver by lazy { MovieBroadCastReceiver() }

    override fun onCreate() {
        super.onCreate()
        ContextCompat.registerReceiver(
            this,
            reservationBroadcastReceiver,
            IntentFilter(RESERVATION_NOTIFICATION_ACTION),
            ContextCompat.RECEIVER_NOT_EXPORTED,
        )
    }

    override fun onTerminate() {
        unregisterReceiver(reservationBroadcastReceiver)
        super.onTerminate()
    }
}
