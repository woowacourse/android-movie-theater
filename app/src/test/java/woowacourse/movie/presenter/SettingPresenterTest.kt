package woowacourse.movie.presenter

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.data.repository.SettingRepositoryImpl
import woowacourse.movie.presentation.view.setting.SettingContract
import woowacourse.movie.presentation.view.setting.SettingPresenter

class SettingPresenterTest {
    private lateinit var presenter: SettingContract.Presenter
    private lateinit var view: SettingContract.View
    private lateinit var settingRepository: SettingRepositoryImpl

    @BeforeEach
    fun setUp() {
        view = mockk(relaxed = true)
        settingRepository = mockk()
        presenter = SettingPresenter(view, settingRepository)
    }

    @Test
    fun `푸시_알람_설정값을_가져와_화면에_보여준다`() {
        // given
        every { settingRepository.getNotificationEnabled() } returns true

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
        verify { settingRepository.setNotificationEnabled(false) }
    }
}
