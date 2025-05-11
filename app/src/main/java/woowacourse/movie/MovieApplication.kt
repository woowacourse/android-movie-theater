package woowacourse.movie

import android.app.Application
import woowacourse.movie.data.SettingPreferenceManager
import woowacourse.movie.data.db.ReservationDao
import woowacourse.movie.data.db.ReservationDatabase
import woowacourse.movie.data.repository.ReservationRepositoryImpl
import woowacourse.movie.data.repository.SettingRepositoryImpl
import woowacourse.movie.domain.repository.ReservationRepository
import woowacourse.movie.domain.repository.SettingRepository
import woowacourse.movie.presentation.alarm.AlarmScheduler
import woowacourse.movie.presentation.view.reservation.seat.SeatSelectContract
import woowacourse.movie.presentation.view.reservation.seat.SeatSelectPresenter
import woowacourse.movie.presentation.view.reservationlist.ReservationListContract
import woowacourse.movie.presentation.view.reservationlist.ReservationListPresenter
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
                reservationRepository = provideReservationRepository(),
            )

        fun provideReservationListPresenter(view: ReservationListContract.View): ReservationListContract.Presenter =
            ReservationListPresenter(
                view = view,
                reservationRepository = provideReservationRepository(),
            )

        fun provideSettingPresenter(view: SettingContract.View): SettingContract.Presenter =
            SettingPresenter(
                view = view,
                settingRepository = provideSettingRepository(),
            )

        fun provideSettingRepository(): SettingRepository = SettingRepositoryImpl(provideSettingPreferenceManager())

        private fun provideReservationDao(): ReservationDao = ReservationDatabase.getInstance(instance).reservationDao()

        private fun provideReservationRepository(): ReservationRepository = ReservationRepositoryImpl(provideReservationDao())

        private fun provideSettingPreferenceManager(): SettingPreferenceManager = SettingPreferenceManager()

        private fun provideAlarmScheduler(): AlarmScheduler = AlarmScheduler(instance)
    }
}
