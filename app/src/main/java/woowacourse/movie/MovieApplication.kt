package woowacourse.movie

import android.app.Application
import woowacourse.movie.data.ReservationProviderImpl
import woowacourse.movie.data.SettingPreferenceManager
import woowacourse.movie.data.SettingRepositoryImpl
import woowacourse.movie.data.db.ReservationDao
import woowacourse.movie.data.db.ReservationDatabase
import woowacourse.movie.domain.ReservationProvider
import woowacourse.movie.domain.SettingRepository
import woowacourse.movie.presentation.alarm.AlarmScheduler
import woowacourse.movie.presentation.view.reservation.seat.SeatSelectContract
import woowacourse.movie.presentation.view.reservation.seat.SeatSelectPresenter
import woowacourse.movie.presentation.view.setting.SettingContract
import woowacourse.movie.presentation.view.setting.SettingPresenter

class MovieApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: MovieApplication
            private set

        fun provideSeatSelectPresenter(view: SeatSelectContract.View): SeatSelectContract.Presenter =
            SeatSelectPresenter(
                view = view,
                provider = provideReservationProvider(),
                alarmScheduler = provideAlarmScheduler(),
            )

        fun provideSettingPresenter(view: SettingContract.View): SettingContract.Presenter =
            SettingPresenter(
                view = view,
                settingRepository = provideSettingRepository(),
            )

        private fun provideReservationDao(): ReservationDao = ReservationDatabase.getInstance(instance).reservationDao()

        private fun provideReservationProvider(): ReservationProvider = ReservationProviderImpl(provideReservationDao())

        private fun provideSettingRepository(): SettingRepository = SettingRepositoryImpl(provideSettingPreferenceManager())

        private fun provideSettingPreferenceManager(): SettingPreferenceManager = SettingPreferenceManager()

        private fun provideAlarmScheduler(): AlarmScheduler = AlarmScheduler(instance)
    }
}
