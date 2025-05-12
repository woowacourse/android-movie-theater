package woowacourse.movie.presentation.setting

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.data.notification.NotificationPreference

class SettingPresenterTest {
    private lateinit var presenter: SettingPresenter
    private lateinit var view: SettingContract.View
    private lateinit var notificationPreference: NotificationPreference

    @BeforeEach
    fun setUp() {
        view = mockk(relaxed = true)
        notificationPreference = mockk(relaxed = true)
        presenter = SettingPresenter(view, notificationPreference)
    }

    @Test
    fun `수신 여부를 확인하고 화면에 출력한다`() {
        // Given
        every { notificationPreference.isNotificationEnabled() } returns true

        // When
        presenter.loadNotificationSetting()

        // Then
        verify { notificationPreference.isNotificationEnabled() }
        verify { view.showNotificationSetting(true) }
    }

    @Test
    fun `수신 여부를 변경한다`() {
        // When
        presenter.changeNotificationSetting(false)

        // Then
        verify { notificationPreference.setNotificationEnabled(false) }
    }
}
