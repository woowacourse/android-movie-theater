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
    private lateinit var settingPresenter: SettingContract.Presenter
    private lateinit var view: SettingContract.View

    @Before
    fun init() {
        view = mockk()
    }

    @Test
    fun 설정_화면을_설정한다() {
        // given
        every { view.makeSettingSwitch() } just runs

        // when
        settingPresenter = SettingPresenter(view)

        // then
        verify { view.makeSettingSwitch() }
    }

    @Test
    fun 알림_스위치를_토글한다() {
        // given
        every { view.makeSettingSwitch() } just runs
        settingPresenter = SettingPresenter(view)

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
