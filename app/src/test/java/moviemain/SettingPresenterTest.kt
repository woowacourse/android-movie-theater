package moviemain

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.data.setting.SettingDataManager
import woowacourse.movie.view.moviemain.setting.SettingContract
import woowacourse.movie.view.moviemain.setting.SettingPresenter

class SettingPresenterTest {
    private lateinit var presenter: SettingContract.Presenter
    private lateinit var view: SettingContract.View

    @Before
    fun setUp() {
        view = mockk()
        every { view.requestNotificationPermission() } returns true
        every { view.setAlarms(any()) } just runs
        every { view.cancelAlarms() } just runs

        val settingManager = object : SettingDataManager {
            private var isAlarm = false
            override fun getIsAlarmSetting(): Boolean {
                return isAlarm
            }

            override fun setIsAlarmSetting(isOn: Boolean) {
                isAlarm = isOn
            }
        }

        presenter = SettingPresenter(view, settingManager)
    }

    @Test
    fun 토글_초기값을_설정할_수_있다() {
        val slot = slot<Boolean>()
        every { view.setToggle(capture(slot)) } just runs
        presenter.initToggle()
        assertEquals(false, slot.captured)
        verify { view.setToggle(slot.captured) }
    }

    @Test
    fun 클릭하면_토글을_ON한다() {
        val slot = slot<Boolean>()
        every { view.setToggle(capture(slot)) } just runs
        presenter.onClick(true)
        assertEquals(true, slot.captured)
        verify { view.setToggle(slot.captured) }
    }

    @Test
    fun ON인_토글을_다시_클릭하면_OFF한다() {
        val slot = slot<Boolean>()
        every { view.setToggle(capture(slot)) } just runs
        presenter.onClick(false)
        assertEquals(false, slot.captured)
        verify { view.setToggle(slot.captured) }
    }
}
