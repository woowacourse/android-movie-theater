package reservationcompleted

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.data.setting.SettingDataManager
import woowacourse.movie.view.model.ReservationUiModel
import woowacourse.movie.view.reservationcompleted.ReservationCompletedContract
import woowacourse.movie.view.reservationcompleted.ReservationCompletedPresenter
import java.time.LocalDateTime

class ReservationCompletedPresenterTest {
    private lateinit var presenter: ReservationCompletedContract.Presenter
    private lateinit var view: ReservationCompletedContract.View
    private val settingManager = object : SettingDataManager {
        private var isAlarm = false
        override fun getIsAlarmSetting(): Boolean {
            return isAlarm
        }

        override fun setIsAlarmSetting(isOn: Boolean) {
            isAlarm = isOn
        }
    }

    @Before
    fun setUp() {
        view = mockk()
        every { view.registerAlarm(any()) } just runs
        presenter = ReservationCompletedPresenter(view, settingManager)
    }

    @Test
    fun 알람_세팅을_결정할_때_세팅값이_false면_알람을_설정하지_않는다() {
        val reservation = ReservationUiModel(
            "해리 포터와 마법사의 돌",
            LocalDateTime.of(2024, 4, 1, 13, 0),
            1,
            listOf("B2"),
            10000,
            "선릉 극장",
        )
        presenter.decideAlarm(reservation)
        verify(exactly = 0) { view.registerAlarm(any()) }
    }

    @Test
    fun 알람_세팅을_결정할_때_세팅값이_true면_알람을_설정한다() {
        presenter.setAlarm(true)
        val reservation = ReservationUiModel(
            "해리 포터와 마법사의 돌",
            LocalDateTime.of(2024, 4, 1, 13, 0),
            1,
            listOf("B2"),
            10000,
            "선릉 극장",
        )
        presenter.decideAlarm(reservation)
        verify(exactly = 1) { view.registerAlarm(any()) }
    }

    @Test
    fun 세팅값을_설정할_수_있다() {
        presenter.setAlarm(true)
        assertEquals(true, settingManager.getIsAlarmSetting())
    }
}
