package woowacourse.movie.presenter

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.data.SettingPreferenceManager
import woowacourse.movie.presentation.view.setting.SettingContract
import woowacourse.movie.presentation.view.setting.SettingPresenter

class SettingPresenterTest {
    private lateinit var presenter: SettingContract.Presenter
    private lateinit var view: SettingContract.View
    private lateinit var preferenceManager: SettingPreferenceManager

    @BeforeEach
    fun setUp() {
        view = mockk(relaxed = true)
        preferenceManager = mockk(relaxed = true)
        presenter = SettingPresenter(view, preferenceManager)
    }

    @Test
    fun `푸시_알람_설정값을_가져와_화면에_보여준다`() {
        // given
        every { preferenceManager.getPushAlarmEnabled() } returns true

        // when
        presenter.fetchSettingInfo()

        // then
        verify { view.showPushAlarmSetting(true) }
    }

    @Test
    fun `푸시_알람_설정값을_저장한다`() {
        // when
        presenter.savePushAlarmSetting(false)

        // then
        verify { preferenceManager.setPushAlarmEnabled(false) }
    }
}
