package woowacourse.movie.presenter.setting

import android.content.SharedPreferences
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.view.setting.SettingFragment.Companion.PUSH_SETTING

class SettingPresenterTest {
    private lateinit var view: SettingContract.View
    private lateinit var presenter: SettingContract.Presenter
    private lateinit var sharedPreferences: SharedPreferences

    @BeforeEach
    fun setUp() {
        view = mockk(relaxed = true)
        sharedPreferences = mockk(relaxed = true)
        presenter = SettingPresenter(view, sharedPreferences)
    }

    @Test
    fun `유저가 알림을 설정하는 스위치를 키면 알림을 허용하는 토스트를 띄어서 유저에게 알린다`() {
        presenter.settingPushAlarmState(isPushSetting = true)

        verify { view.showPushSettingOnToast() }
    }

    @Test
    fun `유저가 알림을 설정하는 스위치를 끄면 알림을 거부하는 토스트를 띄어서 유저에게 알린다`() {
        presenter.settingPushAlarmState(isPushSetting = false)

        verify { view.showPushSettingOffToast() }
    }

    @Test
    fun `설정 화면으로 이동하면 저장된 설정 값을 가져와서 스위치 뷰에 설정 값을 표시해야 한다`() {
        // Given
        val expectedPushSetting = true
        every { sharedPreferences.getBoolean(PUSH_SETTING, any()) } returns expectedPushSetting

        // When
        presenter.loadSavedSetting()

        // Then
        verify { view.showSavedSetting(expectedPushSetting) }
    }
}
