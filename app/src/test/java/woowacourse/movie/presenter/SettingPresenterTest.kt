package woowacourse.movie.presenter

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.movie.contract.SettingContract

class SettingPresenterTest {
    lateinit var settingPresenter: SettingContract.Presenter
    lateinit var view: SettingContract.View

    @Before
    fun init() {
        view = mockk()
        settingPresenter = SettingPresenter(view)
    }

    @Test
    fun initFragment() {
        // given
        every { view.makeSettingSwitch() } just runs

        // when
        settingPresenter.initFragment()

        // then
        verify { view.makeSettingSwitch() }
    }

    @Test
    fun toggleNotificationSetting() {
        // given
        every {
            view.onNotificationSwitchCheckedChangeListener(
                any(), any(), any()
            )
        } just runs

        // when
        settingPresenter.toggleNotificationSetting(mockk(), "", false)

        // then
        verify { view.onNotificationSwitchCheckedChangeListener(any(), any(), any()) }
    }
}
