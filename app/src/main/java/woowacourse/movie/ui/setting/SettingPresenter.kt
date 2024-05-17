package woowacourse.movie.ui.setting

import woowacourse.movie.ReservationHistoryAlarmManager
import woowacourse.movie.db.reservationhistory.ReservationHistoryDatabase
import kotlin.concurrent.thread

class SettingPresenter(
    private val view: SettingContract.View,
    private val db: ReservationHistoryDatabase,
    private val reservationHistoryAlarmManager: ReservationHistoryAlarmManager,
) : SettingContract.Presenter {
    override fun confirmAlarm() {
        thread {
            val reservationHistory = db.reservationHistoryDao().getAll()
            reservationHistory.forEach {
                reservationHistoryAlarmManager.confirmReservationAlarm(it)
            }
        }.join()
    }

    override fun cancelAram() {
        thread {
            val reservationHistory = db.reservationHistoryDao().getAll()
            reservationHistory.forEach {
                reservationHistoryAlarmManager.cancelReservationAlarm(it)
            }
        }.join()
    }
}
