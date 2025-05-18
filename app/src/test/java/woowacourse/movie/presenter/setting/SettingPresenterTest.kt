package woowacourse.movie.presenter.setting

import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.data.storage.NotificationPermissionStorage

class SettingPresenterTest {
    private lateinit var presenter: SettingPresenter
    private lateinit var view: SettingContracts.View
    private lateinit var notificationPermissionStorage: NotificationPermissionStorage

    @BeforeEach
    fun setup() {
        view = mockk()
        notificationPermissionStorage = mockk()
        presenter = SettingPresenter(view, notificationPermissionStorage)
    }

    @Test
    fun `알림 권한을 업데이트하면 알림 권한 허용 여부를 보여준다`() {
        // given:
        every { view.showNotificationPermission(any()) } just Runs
        every { notificationPermissionStorage.notificationPermission } returns true

        // when:
        presenter.updateNotificationPermission()

        // then:
        verify { view.showNotificationPermission(any()) }
    }

    @Test
    fun `알림 권한을 true 로 업데이트하면 알림 권한이 true 인 것을 보여주며, 저장소에 true 로 저장한다`() {
        // given:
        every { notificationPermissionStorage.notificationPermission } returns true
        every { view.showNotificationPermission(any()) } just Runs
        every { notificationPermissionStorage.updateNotificationPermission(any()) } just Runs

        // when:
        presenter.updateNotificationPermission(true)

        // then:
        verify { notificationPermissionStorage.updateNotificationPermission(true) }
        verify { view.showNotificationPermission(true) }
    }

    @Test
    fun `알림 권한을 false 로 업데이트하면 알림 권한이 false인 것을 보여주며, 저장소에 false 로 저장한다`() {
        // given:
        every { notificationPermissionStorage.notificationPermission } returns false
        every { view.showNotificationPermission(any()) } just Runs
        every { notificationPermissionStorage.updateNotificationPermission(any()) } just Runs

        // when:
        presenter.updateNotificationPermission(false)

        // then:
        verify { notificationPermissionStorage.updateNotificationPermission(false) }
        verify { view.showNotificationPermission(false) }
    }

    @AfterEach
    fun finish() {
        clearAllMocks()
    }
}
