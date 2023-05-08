package woowacourse.movie.presenter

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.movie.setting.SettingContract
import woowacourse.movie.setting.SettingPresenter

class SettingPresenterTest {
    lateinit var settingPresenter: SettingContract.Presenter
    lateinit var view: SettingContract.View

    @Before
    fun init() {
        view = mockk()
        settingPresenter = SettingPresenter(view)
    }

    @Test
    fun 설정_화면을_설정한다() {
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
                any(), any()
            )
        } just runs

        // when
        settingPresenter.toggleNotificationSetting("", false)

        // then
        verify { view.onNotificationSwitchCheckedChangeListener(any(), any()) }
    }
}
