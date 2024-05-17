package woowacourse.movie.ui.setting

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.ReservationHistoryAlarmManager
import woowacourse.movie.db.reservationhistory.ReservationHistoryDatabase

class SettingPresenterTest {
    private lateinit var view: SettingContract.View
    private lateinit var db: ReservationHistoryDatabase
    private lateinit var alarmManager: ReservationHistoryAlarmManager
    private lateinit var presenter: SettingContract.Presenter

    @BeforeEach
    fun setup() {
        view = mockk<SettingContract.View>()
        db = mockk<ReservationHistoryDatabase>()
        alarmManager = mockk<ReservationHistoryAlarmManager>()
        presenter = SettingPresenter(view, db, alarmManager)
    }

    @Test
    fun `알람을 켜면 알람이 설정된다`() {
        every { alarmManager.confirmReservationAlarm(any()) } just runs

        presenter.confirmAlarm()

        every { alarmManager.confirmReservationAlarm(any()) }
    }

    @Test
    fun `알람을 끄면 알람이 꺼진다`() {
        every { alarmManager.cancelReservationAlarm(any()) } just runs

        presenter.cancelAram()

        every { alarmManager.cancelReservationAlarm(any()) }
    }
}
