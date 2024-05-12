import android.content.BroadcastReceiver
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import woowacourse.movie.data.DummyMovies
import woowacourse.movie.model.MovieReservation
import woowacourse.movie.setting.AlarmSetting
import woowacourse.movie.setting.SettingContract
import woowacourse.movie.setting.SettingPresenter

class SettingPresenterTest {
    private lateinit var receiver: BroadcastReceiver
    private lateinit var presenter: SettingContract.Presenter
    private lateinit var alarmSetting: AlarmSetting

    @BeforeEach
    fun setUp() {
        receiver = mockk<BroadcastReceiver>(relaxed = true)
        alarmSetting = mockk<AlarmSetting>()
        presenter = SettingPresenter(DummyMovies, alarmSetting)
    }

    @Test
    @DisplayName("presenter에서 setAlarm을 호출하면, alarmSetting의 setAlarm이 호출되어 MovieReservation 객체가 넘어간다.")
    fun alarmSetting_setAlarm_called_when_presenter_setAlarm_called() {
        every { alarmSetting.setAlarm(MovieReservation.STUB) } just Runs

        presenter.setAlarm()
        verify(exactly = 1) { alarmSetting.setAlarm(MovieReservation.STUB) }
    }

    @Test
    @DisplayName("presenter에서 cancelAlarm을 호출하면, alarmSetting의 cancelAlarm이 호출되어 MovieReservation 객체가 넘어간다.")
    fun alarmSetting_cancelAlarm_called_when_presenter_cancelAlarm_called() {
        every { alarmSetting.cancelAlarm(MovieReservation.STUB) } just Runs

        presenter.cancelAlarm()
        verify(exactly = 1) { alarmSetting.cancelAlarm(MovieReservation.STUB) }
    }
}
