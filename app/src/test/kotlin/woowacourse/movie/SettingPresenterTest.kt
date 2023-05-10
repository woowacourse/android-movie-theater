package woowacourse.movie

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import woowacourse.movie.ui.setting.SettingContract
import woowacourse.movie.ui.setting.SettingPresenter
import woowacourse.movie.ui.setting.SettingRepository

class SettingPresenterTest {

    private lateinit var settingPresenter: SettingContract.Presenter
    private lateinit var view: SettingContract.View
    private lateinit var repository: SettingRepository

    @Before
    fun setUp() {
        view = mockk()
        repository = mockk()
        settingPresenter = SettingPresenter(
            view = view,
            settingRepository = repository
        )
    }

    @Test
    fun `저장소로부터 설정값을 받아와서 해당 값으로 view를 세팅한다`() {
        // given
        val slot = slot<Boolean>()

        every { repository.isAvailableAlarm } returns true
        every { view.setSwitch(capture(slot)) } just Runs

        // when
        settingPresenter.loadSetting()

        // then
        val actual = slot.captured
        assertTrue(actual)
        verify { view.setSwitch(actual) }
    }

    @Test
    fun `저장소에 설정값을 저장하고 해당 값으로 view를 세팅한다`() {
        // given
        val slot = slot<Boolean>()
        val settingFlag = true

        every { view.setSwitch(capture(slot)) } just Runs
        every { repository.isAvailableAlarm = capture(slot) } just Runs

        // when
        settingPresenter.saveSetting(settingFlag)

        // then
        val actual = slot.captured
        assertTrue(actual)
        verify { view.setSwitch(actual) }
        verify { repository.isAvailableAlarm = actual }
    }
}