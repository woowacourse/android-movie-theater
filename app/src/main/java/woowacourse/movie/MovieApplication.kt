package woowacourse.movie

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.room.Room
import woowacourse.movie.data.database.MovieDatabase
import woowacourse.movie.data.database.MovieDatabase.Companion.DATABASE_NAME
import woowacourse.movie.data.datasource.SettingPreferenceDataSource
import woowacourse.movie.data.repository.BookingRepositoryImpl
import woowacourse.movie.data.repository.SettingRepositoryImpl

class MovieApplication : Application() {
    val movieDatabase by lazy {
        Room
            .databaseBuilder(this, MovieDatabase::class.java, DATABASE_NAME)
            .allowMainThreadQueries()
            .build()
    }
    val bookingRepository by lazy { BookingRepositoryImpl(movieDatabase.bookingDao()) }
    val settingRepository by lazy { SettingRepositoryImpl(SettingPreferenceDataSource(this)) }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val name = getString(R.string.movie_notification_channel_name)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(MOVIE_NOTIFICATION_CHANNEL_ID, name, importance)
        val notificationManager: NotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    companion object {
        const val MOVIE_NOTIFICATION_CHANNEL_ID = "MOVIE_NOTIFICATION_CHANNEL"
    }
}
