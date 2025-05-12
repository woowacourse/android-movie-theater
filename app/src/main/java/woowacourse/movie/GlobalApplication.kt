package woowacourse.movie

import android.app.Application
import woowacourse.movie.data.ReservationRepositoryImpl
import woowacourse.movie.data.SettingRepositoryImpl
import woowacourse.movie.data.db.ReservationDatabase
import woowacourse.movie.data.preference.NotificationPreferenceManager

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initAppProvider()
    }

    private fun initAppProvider() {
        initSettingRepository()
        initReservationRepository()
    }

    private fun initSettingRepository() {
        val preferenceManager = NotificationPreferenceManager(applicationContext)
        val settingRepository = SettingRepositoryImpl(preferenceManager)
        AppProvider.initSettingRepository(settingRepository)
    }

    private fun initReservationRepository() {
        val dao = ReservationDatabase.getInstance(applicationContext).reservationDao()
        val reservationRepository = ReservationRepositoryImpl(dao)
        AppProvider.initReservationRepository(reservationRepository)
    }
}
