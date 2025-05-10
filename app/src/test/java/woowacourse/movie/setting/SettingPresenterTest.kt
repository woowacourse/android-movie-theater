package woowacourse.movie.setting

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.main.sharedPreference.PreferencesProvider

class SettingPresenterTest {
    private lateinit var presenter: SettingPresenter
    private lateinit var mockView: SettingContract.View
    private lateinit var mockPreference: PreferencesProvider

    @BeforeEach
    fun setUp() {
        mockView = mockk(relaxed = true)
        mockPreference = mockk(relaxed = true)

        presenter =
            SettingPresenter(
                view = mockView,
                preferences = mockPreference,
            )
    }

    @Test
    fun `값에 따라 Switch의 상태를 변경하고 저장된 데이터를 업데이트 할 수 있다`() {
        presenter.setNotificationAlarm(true)

        verify { mockPreference.setAlarmEnabled(true) }
        verify { mockView.showAlarmState() }
    }
}
