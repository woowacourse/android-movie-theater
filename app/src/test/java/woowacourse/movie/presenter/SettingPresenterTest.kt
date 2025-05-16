package woowacourse.movie.presenter

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.data.setting.SettingRepository
import woowacourse.movie.view.setting.SettingContract
import woowacourse.movie.view.setting.SettingPresenter

class SettingPresenterTest {
    private lateinit var view: SettingContract.View
    private lateinit var manager: SettingRepository
    private lateinit var presenter: SettingPresenter

    @BeforeEach
    fun setUp() {
        view = mockk(relaxed = true)
        manager = mockk(relaxed = true)
        presenter = SettingPresenter(view, manager)
    }

    @Test
    fun `알림 기능이 꺼져있을 시 알림 기능 꺼짐 상태를 표시한다`() {
        // given
        every { manager.isNotificationEnabled() } returns false

        // when
        presenter.loadSettings()

        // then
        verify { view.showNotificationSetting(false) }
    }

    @Test
    fun `알림 기능과 알림 권한이 켜져있을 시 알림 기능 켜짐 상태를 표시한다`() {
        // given
        every { manager.isNotificationEnabled() } returns true
        every { view.isNotificationPermitted() } returns true

        // when
        presenter.loadSettings()

        // then
        verify { view.showNotificationSetting(true) }
    }

    @Test
    fun `알림 기능이 꺼진 상태에서 설정 변경을 시도할 시 알림 기능을 켠다`() {
        // given
        every { manager.isNotificationEnabled() } returns false

        // when
        presenter.toggleNotificationSetting()

        // then
        verify { view.attemptNotificationSettingChange(true) }
    }

    @Test
    fun `알림 기능이 켜진 상태에서 설정 변경을 시도할 시 알림 기능을 끈다`() {
        // given
        every { manager.isNotificationEnabled() } returns true

        // when
        presenter.toggleNotificationSetting()

        // then
        verify { view.attemptNotificationSettingChange(false) }
    }

    @Test
    fun `알림 기능을 켜면 켜진 상태를 저장 및 표시한다`() {
        // when
        presenter.setNotificationSetting(true)

        // then
        verify {
            manager.updateNotificationSetting(true)
            view.showNotificationSetting(true)
        }
    }

    @Test
    fun `알림 기능을 끄면 꺼진 상태를 저장 및 표시한다`() {
        // when
        presenter.setNotificationSetting(false)

        // then
        verify {
            manager.updateNotificationSetting(false)
            view.showNotificationSetting(false)
        }
    }
}
