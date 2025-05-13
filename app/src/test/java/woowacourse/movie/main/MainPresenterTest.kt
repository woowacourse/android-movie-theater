package woowacourse.movie.main

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.main.permission.MoviePermissionHandler
import woowacourse.movie.main.sharedPreference.PreferencesProvider

class MainPresenterTest {
    private lateinit var presenter: MainPresenter
    private lateinit var mockView: MainContract.View
    private lateinit var mockPermissionHandler: MoviePermissionHandler
    private lateinit var mockPreference: PreferencesProvider

    @BeforeEach
    fun setUp() {
        mockView = mockk(relaxed = true)
        mockPermissionHandler = mockk(relaxed = true)
        mockPreference = mockk(relaxed = true)

        presenter =
            MainPresenter(
                view = mockView,
                permissionHandler = mockPermissionHandler,
                preferences = mockPreference,
            )
    }

    @Test
    fun `알람 권한이 없는 경우에 SettingAlarm 다이얼로그를 보여준다`() {
        every { mockView.shouldShowNotificationRationale() } returns true

        presenter.requestSettingAlarmPermission()

        verify { mockView.showSettingAlarmDialog() }
    }

    @Test
    fun `처음 알림 권한을 요청하는 경우에 시스템 알림을 띄운다`() {
        every { mockView.shouldShowNotificationRationale() } returns false

        presenter.requestSettingAlarmPermission()

        verify { mockView.requestNotificationPermission() }
    }

    @Test
    fun `모든 권한이 허용되었을 경우 알람을 활성화 상태로 설정한다`() {
        every { mockPermissionHandler.hasAllPermission() } returns true

        presenter.checkAllPermission()

        verify { mockPreference.setAlarmEnabled(true) }
    }
}
