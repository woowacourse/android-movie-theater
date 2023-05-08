package woowacourse.movie.presenter

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.database.SharedPreferenceManager
import woowacourse.movie.view.main.setting.SettingContract
import woowacourse.movie.view.main.setting.SettingPresenter

class SettingPresenterTest {
    private lateinit var presenter: SettingPresenter
    private lateinit var view: SettingContract.View
    private lateinit var settingPreferenceManager: SharedPreferenceManager

    @Before
    fun setUp() {
        view = mockk()
        settingPreferenceManager = mockk()
        presenter = SettingPresenter(view, settingPreferenceManager)
    }

    @Test
    fun 스위치를_클릭하면_알람_수신_상태가_바뀐다() {
        // given
        every { settingPreferenceManager.changeData() } returns Unit
        // when
        presenter.onClickSwitch()
        // then
        verify { settingPreferenceManager.changeData() }
    }

    @Test
    fun Preference_데이터가_true_이면_Switch는_checked_상태이다() {
        // given
        val slot = slot<Boolean>()
        every { settingPreferenceManager.getData() } returns true
        every { view.setSwitchState(capture(slot)) } answers { println("slot = ${slot.captured}") }
        // when
        presenter.updateSwitchState()
        // then
        val actual = slot.captured
        assertEquals(actual, true)
        verify { view.setSwitchState(actual) }
    }
}
