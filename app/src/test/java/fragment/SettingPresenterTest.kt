package fragment

import com.woowacourse.domain.SettingRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.fragment.setting.SettingContract
import woowacourse.movie.fragment.setting.SettingFragment
import woowacourse.movie.fragment.setting.SettingPresenter

class SettingPresenterTest {
    private lateinit var presenter: SettingPresenter
    private lateinit var view: SettingContract.View
    private lateinit var settingRepository: SettingRepository

    @Before
    fun setUp() {
        view = mockk()
        settingRepository = mockk(relaxed = true)
        presenter = SettingPresenter(view, settingRepository)
    }

    @Test
    fun 권한이_있다면_스위치_상태를_꺼놓는다() {
        // given
        val slot = slot<Boolean>()
        every { view.setSwitchStatus(capture(slot)) } returns Unit

        // when
        presenter.initSwitchStatus(false)

        // then
        assertEquals(false, slot.captured)
    }

    @Test
    fun 권한이_없고_true로_저장되어_있다면_true로_지정해놓는다() {
        // given
        val slot = slot<Boolean>()
        every { settingRepository.getValue(SettingFragment.SETTING_PUSH_ALARM_SWITCH_KEY) } returns true
        every { view.setSwitchStatus(capture(slot)) } returns Unit

        // when
        presenter.initSwitchStatus(true)

        // then
        assertEquals(true, slot.captured)
    }
}
