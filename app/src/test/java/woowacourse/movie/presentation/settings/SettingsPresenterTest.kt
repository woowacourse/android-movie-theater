package woowacourse.movie.presentation.settings

import android.content.Context
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.fixture.repository.FakeSettingRepository

class SettingsPresenterTest {
    private lateinit var view: SettingsContract.View
    private lateinit var presenter: SettingsContract.Presenter

    @BeforeEach
    fun setUp() {
        view = mockk(relaxed = true)
        val context: Context = mockk(relaxed = true)
        val settingRepository = FakeSettingRepository(isSaved = true, isGranted = true)
        presenter = SettingsPresenter(view, context, settingRepository)
    }

    @Test
    fun `알림 설정 상태를 출력한다`() {
        // when
        presenter.loadSettings()

        // then
        verify { view.updateNotificationSetting(true) }
    }
}
