package woowacourse.movie.presenter

import android.content.SharedPreferences
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.feature.setting.contract.SettingContract
import woowacourse.movie.feature.setting.presenter.SettingPresenter

@Suppress("ktlint:standard:function-naming")
class SettingPresenterTest {
    private lateinit var presenter: SettingPresenter
    private lateinit var mockView: SettingContract.View
    private lateinit var mockSharedPreferences: SharedPreferences
    private lateinit var mockEditor: SharedPreferences.Editor

    @BeforeEach
    fun setup() {
        mockView = mockk(relaxed = true)
        mockSharedPreferences = mockk(relaxed = true)
        mockEditor = mockk(relaxed = true)
        every { mockSharedPreferences.edit() } returns mockEditor
        every { mockEditor.putBoolean(any(), any()) } returns mockEditor
        presenter = SettingPresenter(mockView, mockSharedPreferences)
    }

    @Test
    fun 알림_설정을_SharedPreferences에서_올바르게_불러온다() {
        // given
        every { mockSharedPreferences.getBoolean("NOTIFICATION_ENABLED", false) } returns true

        // when
        presenter.loadNotificationSettings()

        // then
        verify { mockView.setNotificationSwitchChecked(true) }
    }

    @Test
    fun 알림_설정을_SharedPreferences에서_false로_불러오면_스위치가_해제된다() {
        // given
        every { mockSharedPreferences.getBoolean("NOTIFICATION_ENABLED", false) } returns false

        // when
        presenter.loadNotificationSettings()

        // then
        verify { mockView.setNotificationSwitchChecked(false) }
    }

    @Test
    fun 알림_스위치를_활성화하면_SharedPreferences를_true로_업데이트하고_권한_요청을_표시한다() {
        // given
        val isChecked = true

        // when
        presenter.toggleNotificationSwitch(isChecked)

        // then
        verify {
            mockEditor.putBoolean("NOTIFICATION_ENABLED", true)
            mockEditor.apply()
            mockView.showNotificationPermissionRequest()
        }
    }

    @Test
    fun 알림_스위치를_비활성화하면_SharedPreferences를_false로_업데이트하고_권한_요청을_표시하지_않는다() {
        // given
        val isChecked = false

        // when
        presenter.toggleNotificationSwitch(isChecked)

        // then
        verify { mockEditor.putBoolean("NOTIFICATION_ENABLED", false) }
        verify { mockEditor.apply() }
        verify(exactly = 0) { mockView.showNotificationPermissionRequest() }
    }
}
