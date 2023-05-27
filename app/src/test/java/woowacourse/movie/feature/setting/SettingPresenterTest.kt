package woowacourse.movie.feature.setting

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test
import woowacourse.movie.data.setting.AlarmSetting

internal class SettingPresenterTest {

    lateinit var presenter: SettingContract.Presenter
    lateinit var view: SettingContract.View
    lateinit var alarmSetting: AlarmSetting

    @Before
    fun setUp() {
        alarmSetting = mockk()
        view = mockk()
        presenter = SettingPresenter(view, alarmSetting)
    }

    @Test
    fun `저장된_알림_설정값으로_view를_설정한다`() {
        // given
        val slot = slot<Boolean>()

        every { alarmSetting.isEnable } returns true
        every { view.setMovieReminderChecked(capture(slot)) } just Runs

        // when
        presenter.setMovieReminderChecked(true)

        // then
        val actual = slot.captured
        assertTrue(actual)
        verify { view.setMovieReminderChecked(actual) }
    }

//    @Test
//    fun `alarmSetting에_설정값을_저장하고_view를_설정한다`() {
//        // given
//        val slot = slot<Boolean>()
//        val settingFlag = true
//
//        every { view.setMovieReminderChecked(capture(slot)) } just Runs
//        every { alarmSetting.enabled = capture(slot) } just Runs
//
//        // when
//        presenter.setMovieReminderChecked(settingFlag)
//
//        // then
//        val actual = slot.captured
//        assertTrue(actual)
//        verify { view.setMovieReminderChecked(actual) }
//        verify { alarmSetting.enabled = actual }
//    }
}
